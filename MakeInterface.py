import re
from sys import argv
#Colin 23-1-2024
#Helper Utility to write wrapper functions using Java Foreign Function Interface from JDK 21
#Handles arrays of strings properly after intense web search; need the reinterpret step
#run: To make the stub for pickoutstrings (get definition from OptimiserContoller.java)
#python Makeinterface.py "int pickoutstrings(long nstocks, String[] stocklist, long M_nstocks, String[] M_stocklist, String[] Q, long[] Order)"
line='String Return_Message(int ifail)'
i=0
for arg in argv:
    if i!=0:line=arg.strip()
    #print(line)
    if i==1:break
    i+=1
haveObject=0
RR=''
if line.find('Object')>-1:haveObject=1
allargs=[re.sub('^.* ','',part) for part in (line.split('(')[1].replace(')','')).split(',')]
#Attempt to get the arguments correct for java's twoD2oneD, but check the final outcome
returnObj=line.split(' ')[0].strip()
funcName=re.sub('\(.*','',line.split(' ')[1]).strip()
parts=re.sub('^.*\(','',line)
parts=re.sub('\).*','',parts).split(',')
print('public static ',line,'{')
if returnObj!='void':
    if returnObj=='String':print('%s back="";'%(returnObj))
    else :print('%s back=-12345;'%(returnObj))
if haveObject==1:    
    print('/* If an argument is of type Object it will mean that')
    print('it is a function. The java generated here is not complete.')
    print('It is only 100% correct for Solve1D, otherwise some editing will be needed*/')
    RR=line.split('Object')[1].strip().split(',')[0]
    print('')
    print('MethodHandle mh = null;')
    print('MemorySegment ms = null;')
    print('FunctionDescriptor oned;')
    print('MethodHandles.Lookup lookup = MethodHandles.lookup(); try {')
    print('mh = lookup.findStatic(Info.class, "passerFunc",')
    print('MethodType.methodType(double.class, double.class, MemorySegment.class));')
    print('oned = FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, ValueLayout.JAVA_DOUBLE,ValueLayout.ADDRESS);')
    print('ms = Linker.nativeLinker().upcallStub(mh, oned, Arena.ofAuto());')
    print('} catch (Throwable u) {')
    print('System.out.println(u);\t}')
    print('\n\ttry (Arena foreign = Arena.ofConfined()) {','\n\tfinal var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);')
    print('mh = MethodHandles.publicLookup().findStatic(%s.getClass(), "getseek",'%(RR))
    print('\tMethodType.methodType(double.class, Object.class));')
    print('var risk =     (double) mh.invokeExact(RiskE);')
    print('var %s%s = foreign.allocate(8);'%(RR,RR))
    print('%s%s.set(ValueLayout.JAVA_DOUBLE, 0, risk);'%(RR,RR))

if haveObject==0:print('\n\ttry (Arena foreign = Arena.ofConfined()) {','\n\tfinal var safeqp = SymbolLookup.libraryLookup(libraryname, foreign);')
if returnObj=='void':
    print('\tvar %snative = Linker.nativeLinker().downcallHandle('%(funcName))
    print('\tsafeqp.find("%s").orElseThrow(),'%funcName)
else: 
    print('\tvar %snative = Linker.nativeLinker().downcallHandle('%(funcName))
    print('\tsafeqp.find("%s").orElseThrow(),'%funcName)
if returnObj=='void':print('\tFunctionDescriptor.ofVoid(')
elif returnObj=='double' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_DOUBLE,')
elif returnObj=='float' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_FLOAT,')
elif returnObj=='long' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_LONG,')
elif returnObj=='String' :print('\tFunctionDescriptor.of(ValueLayout.ADDRESS,')
elif returnObj=='int' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_INT,')
elif returnObj=='short' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_SHORT,')
elif returnObj=='byte' :print('\tFunctionDescriptor.of(ValueLayout.JAVA_BYTE,')
elif returnObj=='byte[]' :print('\tFunctionDescriptor.of(ValueLayout.ADDRESS,')
#print(parts)
np=len(parts)
ip=0
ending=','
if haveObject:
    for ip in range(np):
        if parts[ip].find(RR):
            parts[ip]=parts[ip].replace(RR,'ms')
    parts.append('MemorySegment %s'%(RR+RR))
np=len(parts)
ip=0
for part in parts:
    part=part.strip()
    if ip==(np-1):ending='));'
    argtype=part.split(' ')[0]
    if argtype.find('[')>-1:argtype='address'
    if argtype=='double':print('\t\tValueLayout.JAVA_DOUBLE',ending)
    elif argtype=='long':print('\t\tValueLayout.JAVA_LONG',ending)
    elif argtype=='MemorySegment':print('\t\tValueLayout.ADDRESS',ending)
    elif argtype=='Object':print('\t\tValueLayout.ADDRESS',ending)
    elif argtype=='String':print('\t\tValueLayout.ADDRESS',ending)
    elif argtype=='int':print('\t\tValueLayout.JAVA_INT',ending)
    elif argtype=='short':print('\t\tValueLayout.JAVA_SHORT',ending)
    elif argtype=='address':print('\t\tValueLayout.ADDRESS',ending)
    ip+=1

for part in parts:
    part=part.strip()
    argtype=part.split(' ')[0].strip()
    if argtype=='String':
        argname=part.split(' ')[1].strip()
        print('var %s%s = foreign.allocateUtf8String(%s);'%(argname,argname,argname))
        print('%s%s.setUtf8String(0,%s);'%(argname,argname,argname))
    if argtype.find('[')>-1:
        argname=part.split(' ')[1].strip()
        if argtype.find('][]')>-1:
            if argname=='FLOAD':print('\tdouble[] %s1d = twoD2oneD((int) %s, (int) %s, %s);\t//Get the integer arguments correct!'%(argname,allargs[0],allargs[1],argname))
            if argname=='AAA':print('\tdouble[] %s1d = twoD2oneD((int) %s, (int) %s, %s);\t//Get the integer arguments correct!'%(argname,allargs[4],allargs[0],argname))
            if argname=='Abs_A':print('\tdouble[] %s1d = twoD2oneD((int) %s, (int) %s, %s);\t//Get the integer arguments correct!'%(argname,'nabs',allargs[0],argname))
            argname+='1d'
        jtype=re.sub('\[.*','',argtype).upper()
        #print('if(%s==null)%s=new %s[0];'%(argname,argname,re.sub('\[.*','',argtype)))
        if jtype=='STRING':
            print(('MemorySegment %s;')%(argname+argname))
            print('if(%s==null){'%(argname))
            print('\t%s = MemorySegment.NULL;}'%(argname+argname))
            print('else{\t%s = foreign.allocateArray(ValueLayout.ADDRESS, %s.length);}'%(argname+argname,argname))
        else:
            print(('MemorySegment %s;')%(argname+argname))
            print('if(%s==null){'%(argname))
            print('\t%s = MemorySegment.NULL;}'%(argname+argname))
            print('else{\t%s = foreign.allocateArray(ValueLayout.JAVA_%s, %s.length);}'%(argname+argname,jtype,argname))
        print('if(%s!=null){'%(argname))
        print('\tfor (int i = 0; i < %s.length; i++) {'%(argname))
        if jtype=='STRING':
            print('\t\tMemorySegment k5=foreign.allocateUtf8String(%s[i]);'%(argname))
            print('\t\tk5.setUtf8String(0,%s[i]);'%(argname))
            print('\t\t%s.setAtIndex(ValueLayout.ADDRESS, i, k5);}}'%(argname+argname))
        else:
            print('\t\t%s.setAtIndex(ValueLayout.JAVA_%s, i, %s[i]);}}'%(argname+argname,jtype,argname))
    
    
if returnObj=='void':print('\t%snative.invokeExact('%(funcName))
elif returnObj!='String':print('\tback = (%s) %snative.invokeExact('%(returnObj,funcName))
else:
    print('\tMemorySegment bbb = (MemorySegment) %snative.invokeExact('%(funcName))
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
    else:
        if argtype=='String':print('\t\t%s%s %s'%(  argname,argname,ending))
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
        print('\tif(%s!=null) for (int i = 0; i < %s.length; i++) {'%(argname,argname))
        if jtype=='STRING':
            print('\t\tvar k8=%s.getAtIndex(ValueLayout.ADDRESS, i);'%(argname+argname))
            print('\t\tk8 = k8.reinterpret(Long.MAX_VALUE);// This is essential')
            print('\t\t%s[i] = k8.getUtf8String(0);}'%argname)
        else:
            print('\t\t%s[i]=%s.getAtIndex(ValueLayout.JAVA_%s, i);}'%(argname,argname+argname,jtype))
    else:
        argname=part.split(' ')[1].strip()
        if argtype=='String':print('%s=%s%s.getUtf8String(0);'%(argname,argname,argname))

if returnObj=='String':    
    print('bbb = bbb.reinterpret(Long.MAX_VALUE);')
    print('back = bbb.getUtf8String(0);')
print('\t}')
if returnObj.find('void')==-1:
    if returnObj=='String':print('\tcatch (Throwable e) {       System.out.println(e);       back = "";       }')
    else:print('\tcatch (Throwable e) {       System.out.println(e);       back = 0;       }')
    print('\treturn back;}')
else :
    print('\tcatch (Throwable e) {       System.out.println(e);             }}')
