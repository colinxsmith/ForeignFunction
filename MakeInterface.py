import re
from sys import argv
#Colin 23-1-2024
#Helper Utility to write wrapper functions using Java Foreign Function Interface from JDK 21
#Handles arrays of strings properly after intense web search; need the reinterpret step
#run: To make the stub for pickoutstrings (get definition from OptimiserContoller.java)
#python Makeinterface.py "int pickoutstrings(long nstocks, String[] stocklist, long M_nstocks, String[] M_stocklist, String[] Q, long[] Order)"
line='void daddvec(long n, double[] a, double[] b, double[] c)'
i=0
for arg in argv:
    if i!=0:line=arg.strip()
    #print(line)
    if i==1:break
    i+=1
allargs=[re.sub('^.* ','',part) for part in (line.split('(')[1].replace(')','')).split(',')]
#Attempt to get the arguments correct for java's twoD2oneD, but check the final outcome
if len(allargs)>1:
    if allargs[1]=='m':
        dd=allargs[0]
        allargs[0]=allargs[1]
        allargs[1]=dd
returnObj=line.split(' ')[0].strip()
funcName=re.sub('\(.*','',line.split(' ')[1]).strip()
parts=re.sub('^.*\(','',line)
parts=re.sub('\).*','',parts).split(',')
print('public static ',line,'{')
if returnObj=='void':
    print('\n\ttry (Arena foreign = Arena.ofConfined()) {','\n\tfinal var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);')
    print('\tvar %snative = Linker.nativeLinker().downcallHandle('%(funcName))
    print('\tsafeqp.find("%s").orElseThrow(),'%funcName)
else: 
    print('\t',returnObj,'back;','\n\ttry (Arena foreign = Arena.ofConfined()) {','\n\tfinal var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);')
    print('\tvar %snative = Linker.nativeLinker().downcallHandle('%(funcName))
    print('\tsafeqp.find("%s").orElseThrow(),'%funcName)
if returnObj=='void':print('\tFunctionDescriptor.ofVoid(')
elif returnObj=='double' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_DOUBLE,')
elif returnObj=='float' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_FLOAT,')
elif returnObj=='long' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_LONG,')
elif returnObj=='int' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_INT,')
elif returnObj=='byte' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_BYTE,')
elif returnObj=='byte[]' :print('\tFunctionDescriptor.of(ValueLayout.ADDRESS,')
#print(parts)
np=len(parts)
ip=0
ending=','
for part in parts:
    part=part.strip()
    if ip==(np-1):ending='));'
    argtype=part.split(' ')[0]
    if argtype.find('[')>-1:argtype='address'
    if argtype=='double':print('\t\tValueLayout.JAVA_DOUBLE',ending)
    elif argtype=='long':print('\t\tValueLayout.JAVA_LONG',ending)
    elif argtype=='int':print('\t\tValueLayout.JAVA_INT',ending)
    elif argtype=='address':print('\t\tValueLayout.ADDRESS',ending)
    ip+=1

for part in parts:
    part=part.strip()
    argtype=part.split(' ')[0].strip()
    if argtype.find('[')>-1:
        argname=part.split(' ')[1].strip()
        if argtype.find('][]')>-1:
            print('\tdouble[] %s1d = twoD2oneD((int) %s, (int) %s, %s);\t//Get the integer arguments correct!'%(argname,allargs[0],allargs[1],argname))
            argname+='1d'
        jtype=re.sub('\[.*','',argtype).upper()
        if jtype=='STRING':
            print('\tvar %s = foreign.allocateArray(ValueLayout.ADDRESS, %s.length);'%(argname+argname,argname))
        else:
            print('\tvar %s = foreign.allocateArray(ValueLayout.JAVA_%s, %s.length);'%(argname+argname,jtype,argname))
        print('\tfor (int i = 0; i < %s.length; i++) {'%(argname))
        if jtype=='STRING':
            print('\t\tMemorySegment k5=foreign.allocateUtf8String(%s[i]);'%(argname))
            print('\t\tk5.setUtf8String(0,%s[i]);'%(argname))
            print('\t\t%s.setAtIndex(ValueLayout.ADDRESS, i, k5);}'%(argname+argname))
        else:
            print('\t\t%s.setAtIndex(ValueLayout.JAVA_%s, i, %s[i]);}'%(argname+argname,jtype,argname))
    
    
if returnObj=='void':print('\t%snative.invokeExact('%(funcName))
else:print('\tback = (%s) %snative.invokeExact('%(returnObj,funcName))
ip=0
ending=','
for part in parts:
    if ip==(np-1):ending=');'
    part=part.strip()
    argtype=part.split(' ')[0].strip()
    argname=part.split(' ')[1].strip()
    if argtype.find('[')>-1:
        if argtype.find('][]')>-1:
            argname+='1d'
        print('\t\t%s %s'%(argname+argname,ending))
    else:print('\t\t%s %s'%(  argname,ending))
    ip+=1


for part in parts:
    part=part.strip()
    argtype=part.split(' ')[0].strip()
    if argtype.find('[')>-1:
        argname=part.split(' ')[1].strip()
        if argtype.find('][]')>-1:
            argname+='1d'
        jtype=re.sub('\[.*','',argtype).upper()
        print('\tfor (int i = 0; i < %s.length; i++) {'%(argname))
        if jtype=='STRING':
            print('\t\tvar k8=%s.getAtIndex(ValueLayout.ADDRESS, i);'%(argname+argname))
            print('\t\tk8 = k8.reinterpret(Long.MAX_VALUE);// This is essential')
            print('\t\t%s[i] = k8.getUtf8String(0);}'%argname)
        else:
            print('\t\t%s[i]=%s.getAtIndex(ValueLayout.JAVA_%s, i);}'%(argname,argname+argname,jtype))
print('\t}')
if returnObj.find('void')==-1:
    print('\tcatch (Throwable e) {       System.out.println(e);       back = 0;       }')
    print('\treturn back;}')
else :
    print('\tcatch (Throwable e) {       System.out.println(e);             }}')
