import re
from sys import argv
#Colin 23-1-2024
#Helper Utility to write wrapper functions using Java Foreign Function Interface from JDK 21
line='void daddvec(long n, double[] a, double[] b, double[] c)'
i=0
for arg in argv:
    if i!=0:line=arg.strip()
    #print(line)
    if i==1:break
    i+=1
returnObj=line.split(' ')[0].strip()
funcName=re.sub('\(.*','',line.split(' ')[1]).strip()
parts=re.sub('^.*\(','',line)
parts=re.sub('\).*','',parts).split(',')
print('public static ',line,'{')
if returnObj=='void':
    print('\n\ttry (Arena foreign = Arena.ofConfined()) {','\n\tfinal var safeqp = SymbolLookup.libraryLookup("safeqp.dll", foreign);')
    print('\tvar %snative = Linker.nativeLinker().downcallHandle('%(funcName))
    print('\tsafeqp.find("%s").orElseThrow(),'%funcName)
else: 
    print('\t',returnObj,'back;','\n\ttry (Arena foreign = Arena.ofConfined()) {','\n\tfinal var safeqp = SymbolLookup.libraryLookup("safeqp.dll", foreign);')
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
        jtype=re.sub('\[.*','',argtype).upper()
        print('var %s = foreign.allocateArray(ValueLayout.JAVA_%s, %s.length);'%(argname+argname,jtype,argname))
        print('for (int i = 0; i < %s.length; i++) {'%(argname))
        print('\t%s.setAtIndex(ValueLayout.JAVA_%s, i, %s[i]);}'%(argname+argname,jtype,argname))
    
    
if returnObj=='void':print('%snative.invokeExact('%(funcName))
else:print('back = (%s) %snative.invokeExact('%(returnObj,funcName))
ip=0
ending=','
for part in parts:
    if ip==(np-1):ending=');'
    part=part.strip()
    argtype=part.split(' ')[0].strip()
    argname=part.split(' ')[1].strip()
    if argtype.find('[')>-1:print('%s'%(argname+argname)),print(ending)
    else:print(argname,ending)
    ip+=1


for part in parts:
    part=part.strip()
    argtype=part.split(' ')[0].strip()
    if argtype.find('[')>-1:
        argname=part.split(' ')[1].strip()
        jtype=re.sub('\[.*','',argtype).upper()
        print('for (int i = 0; i < %s.length; i++) {'%(argname))
        print('\t%s[i]=%s.getAtIndex(ValueLayout.JAVA_%s, i);}'%(argname,argname+argname,jtype))
print('}')
if returnObj.find('void')==-1:
    print('catch (Throwable e) {       System.out.println(e);       back = 0.0;       }')
    print('return back;}')
else :
    print('catch (Throwable e) {       System.out.println(e);             }}')