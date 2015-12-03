<html>
<body>
<h1>Action Language for UML (Alf) Open Source Implementation</h1>
<em>Copyright &copy; 2011-2015 Data Access Technologies, Inc. (Model Driven Solutions)</em>
<br>
<p>
Alf is an action language for UML developed in response to the OMG <em>Concrete 
Syntax for a UML Action Language</em> Request for Proposals. The Alf specification 
document can be found at
<a href="http://www.omg.org/spec/ALF">http://www.omg.org/spec/ALF</a>.
(See also 
<a href="http://solitaire.omg.org/issues/task-force/ALF11">http://solitaire.omg.org/issues/task-force/ALF11</a>
for the list of open issues currently being addressed by the Alf 1.1 Revision
Task Force.)
<p>
This implementation is based on the Alf 1.0.1 specification.  It compiles Alf source text to the executable 
Foundational UML (fUML) subset of UML. The compilation can target either of two fUML execution engine implementations:
<ul>
<li> The fUML Reference Implementation execution engine 
(see <a href="http://fuml.modeldriven.org">http://fuml.modeldriven.org</a>), v1.1.4.  
<a href="https://github.com/ModelDriven/fUML-Reference-Implementation/releases/tag/v1.1.4">
Version 1.1.4</a> (this version includes updates for all issue resolutions approved for fUML 1.2, as well
as an "urgent issue" resolution to be included in fUML 1.2.1). </li>
<li> The fUML execution engine from the Moka framework for model execution in the Eclipse Papyrus tool
(see <a href="http://wiki.eclipse.org/Papyrus/UserGuide/ModelExecution">http://wiki.eclipse.org/Papyrus/UserGuide/ModelExecution</a>),
v1.0.0 or later.</li>
</ul>

<h2>Licensing</h2>
<p>
Data Access Technology, Inc. is copyright owner of the source code for this implementation. 
For licensing terms, see <tt>LICENSING.txt</tt>.

<h2>Installation</h2>
<p>
This file <tt>alf.zip</tt> unzips into the installation directory for Alf. This directory
includes Unix (BASH) shell scripts and DOS batch files for running Alf. By
default, the <tt>Libraries</tt> and <tt>UML/Libraries</tt> subdirectories are expected 
to be in the same directory as the scripts. If you move them, set the environment variables 
<tt>ALF_LIB</tt> and <tt>UML_LIB</tt>, respectively, to their paths.
<p>
The file <tt>alf-src.zip</tt> unzips into four <a href="http://www.eclipse.org">Eclipse</a> projects that, together,
include the full source of the implementation. Eclipse 4.4 or later is required.

<ul>
	<li><tt>org.modeldriven.alf</tt> - This is the base project, including the
	Alf parser, static semantic checking and generic mapping to fUML. However,
	it is not dependent on any specific fUML implementation.</li>
	
	<li><tt>org.modeldriven.alf.fuml.impl</tt> - This project extends the base
	project to specialize the mapping to target the <a href="http://fuml.modeldriven.org">fUML Reference Implementation</a>
	and to allow compiled Alf text to be executed. It does not depend on the
	fUML Reference Implementation project directly but, rather, uses an exported
	<tt>fuml.jar</tt> file.</li>
	
	<li><tt>org.modeldriven.alf.eclipse</tt> - This project extends the base
	project to specialize the mapping to target the 
	<a href="https://projects.eclipse.org/projects/modeling.mdt.uml2"> Eclipse UML2 </a>
	metamodel implementation
	(v5.0.1 or later). It depends on Eclipse plugins and must be built in an Eclipse environment.</li>
	
	<li><tt>org.modeldriven.alf.eclipse.papyrus</tt> - This project extends the
	<tt>org.modeldriven.alf.eclipse</tt> project to allow compiled Alf text to be executed
	using the fUML execution engine from the 
	<a href="http://wiki.eclipse.org/Papyrus/UserGuide/ModelExecution">Moka framework</a> for model execution in
	the <a href="http://www.eclipse.org/papyrus">Eclipse Papyrus</a> modeling tool (v1.0.0 or later).
	It depends on Eclipse plugins and must be built in an Eclipse environment.</li>
</ul>

<h2>Unit Resolution</h2>
<p>
Since this implementation is not embedded in a UML tool, Alf units are managed
as files. During the static semantic analysis of a unit, if an element of
another unit is referenced, then that unit is parsed into memory on demand from
the file corresponding to the unit in order to continue the checking of the
original unit.
<p>
Each Alf unit is expected to be contained in a single file, and each such file
must contain exactly one Alf unit. The files are contained in a directory
structure based on the qualified names of the units.
<h3>The Model Directory</h3>
<p>
All user unit files corresponding to a single "user model" should be placed in
a <em>model directory</em> structured as described above. By default, the model directory
is the <tt>Models</tt> subdirectory of the installation directory, but this
may be overridden using a command line option.
<p>
For example, with the default location for the model directory, a package with the 
qualified name
<p>
<tt>'Property Management'::'Data Model'::Properties</tt>
<p>
would be expected to be found in the file
<p>
<tt>Models/Property Management/Data Model/Properties.alf</tt>
<p>
Subunits of <tt>Properties</tt> are then found in the corresponding subdirectory
<p>
<tt>Models/Property Management/Data Model/Properties</tt>
<p>
<p>
The <em>file</em> <tt>Properties.alf</tt> contains the Alf 
definition for the <tt>Properties</tt> package, while the 
<em>subdirectory</em> <tt>Properties</tt> groups the files for subunits 
of that package (e.g., the class <tt>Properties::Property</tt> is found in the file
<tt>Property.alf</tt> in the <tt>Properties</tt> directory.).
<p>
<em>Note that all characters in an unrestricted name (but not the surrounding 
quote characters) are currently carried over to the file path without change. 
Thus, characters that cannot be supported in file names must be avoided in unit
names.</em>
<p>
All namespace declarations are currently expected to resolve to Alf units.
Therefore, only units without namespace declarations are considered to be model
units. The model scope for such units is all the model units within the same
model directory (i.e., "in the same model"). Thus, one model unit may refer to 
another model unit located in the same model directory, without the need for
explicitly importing the other unit. References to any other elements of another
unit requires an import from the other unit (which is done implicitly for
units from the Alf library). Note, however, that any elements imported
into a unit are also available to all subunits without those subunits having to
also import them.

<h3>The Library Directory</h3>
<p>
The <em>library directory</em> contains Alf unit definitions for the fUML
Foundational Model Library and the Alf Standard Model Library. These units
should not be moved from the library directory structure or changed. By default, 
the library directory is the <tt>Libraries</tt> subdirectory of the installation 
directory, but this may be changed using a command line option or by setting the 
<tt>ALF_LIB</tt> environment variable.
<p>
For example, with the default location for the library directory, the standard 
package <tt>Alf::Library::PrimitiveBehaviors</tt> is found in
the file 
<p>
<tt>Libraries/Alf/Library/PrimitiveBehaviors.alf</tt>
<p>
Subunits of <tt>PrimitiveBehaviors</tt> are then found in the corresponding 
subdirectory
<p>
<tt>Libraries/Alf/Library/PrimitiveBehaviors</tt>

<h3>The UML Library Directory</h3>
<p>
The <em>UML library directory</em> contains compiled versions of the fUML Foundational
Model Library and Alf Standard Model Library implementations, as Eclipse UML2 files.
These files must be kept together in one directory. By default, the UML library
directory is the <tt>UML/Libraries</tt> subdirectory of the installation
directory, but this may be changed using a command line option or by setting the
<tt>UML_LIB</tt> environment variable.

<h2>Unit Execution</h2>
<p>
To be executable, a unit must be the definition of either an activity with no 
parameters or a non-abstract active class with a classifier behavior. 
Execution of such a unit proceeds as follows:

<ol>

<li>The specified unit is parsed into an abstract syntax representation. Any units imported by 
the specified unit are also, recursively, parsed. If there is a syntax 
error, this is reported and processing ends.
<em>(Note: Since units cannot be "pre-compiled" when using the fUML Reference Implementation,
the entire content of the library directory are parsed and loaded on
each execution. When using the Eclipse implementation, the pre-compiled library models in the
UML library directory are used instead.)</em> </li>

<li>All parsed units are checked for violations of the abstract syntax
constraints defined in the Alf specification. If there are any constraint
violations, they are reported and processing ends. Constraint violation messages 
take the form 
<br><br>
<tt><em>constraintName</em> in <em>fileName</em> at line <em>m</em>, column <em>n</em></tt>
<br><br>
The <tt><em>constraintName</em></tt> is the name of the violated constraint, as given in
the Alf specification. <em>(Note 1: Some constraints have additional conditions
implemented over what is currently given in the specification. Note 2: If the
fileName has a suffix of the form <tt>&lt;...&gt;</tt>, then the constraint
violation happened in a template instantiation. This should only happen if the
error actually exists in the template being instantiated.)</em></li>

<li>All parsed units are mapped to fUML. If the fUML Reference Implementation is being used,
this mapping is to an in-memory representation. If the Eclipse UML2 implementation is being
used, the fUML output is written to an Eclipse UML2 file.
<em>(Note: If a mapping error occurs, processing
ends. However, if there are no constrain violations, mapping should always
succeed, so a mapping failure indicates a system implementation error.)</em></li>

<li>If the fUML Reference Implementation is being used, the unit is executed immediately after
compilation. If the Eclipse UML2 implementation is being used, then the output Eclipse UML2 file
can be executed after compilation. In either case, on execution, an execution environment is 
created at a fUML locus, and the unit mapping is
executed in this environment. The console is used for standard input and output.
Execution tracing is at the specified debug level, to the console and the 
file <tt>alf.log</tt> (unless this configuration is changed in 
<tt>log4j.properties</tt>). (<em>Note: Execution tracing is currently only available for
the fUML Reference Implementation.</em>)</li>

</ol>

<h2>Command Line Scripts</h2>
<h3> <tt>alf</tt>: Model execution using the fUML Reference Implementation</h3>
<p>
A model can be executed from the command line using the <tt>alf</tt> shell 
script (for Unix) or the <tt>alf.bat</tt> batch file (for Windows/DOS). The model is
compiled to an in-memory representation and executed using the fUML Reference
Implementation. Usage is
<p>
<tt>alf [<em>options</em>] <em>unitName</em></tt>
<p>
where <tt><em>unitName</em></tt> is the fully qualified name of a unit to be executed.
The allowable <tt><em>options</em></tt> are
<p>
<tt>-d <em>level</em></tt>
<br>
Sets the debug level for trace output from the fUML execution engine. Useful
levels are:
<ul>
<li><tt>OFF</tt> turns off trace output.</li>
<li><tt>ERROR</tt> reports only serious errors (such as when a primitive
behavior implementation cannot be found during execution).</li>
<li><tt>INFO</tt> outputs basic trace information on the execution of activities
and actions.</li>
<li><tt>DEBUG</tt> outputs detailed trace information on activity execution.</li>
</ul>
The default is as configured in the <tt>log4j.properties</tt> file in the
installation directory.
<p>
<tt>-f</tt>
<br>
Treat the <tt><em>unitName</em></tt> as a file name, rather than as a qualified
name. The named file is expected to be found directly in the model directory
and the unit must have the same name as the file name (with any <tt>.alf</tt>
extension removed).  
<p>
<tt>-l <em>path</em></tt>
<br>
Sets the library directory location to <tt><em>path</em></tt>. If this option is not
given and the ALF_LIB environment variable is set, then the value of ALF_LIB is 
used as the library directory location. Otherwise, the default of <tt>Libraries</tt>
is used.
<p>
<tt>-m <em>path</em></tt>
<br>
Sets the model directory location to <tt><em>path</em></tt>. Qualified name resolution to
unit file paths is relative to the root of the model directory. If this option
is not given, the default of <tt>Models</tt> is used.
<p>
<tt>-p</tt>
<br>
Parse and constraint check the identified unit, but do not execute it. This is
useful for syntactic and static semantic validation of units that are not
executable by themselves.
<p>
<tt>-P</tt>
<br>
Parse and constraint check, as for the <tt>-p</tt> option, and then print out the
resulting abstract syntax tree. Note that the print out will occur even if there
are constraint violations.
<p>
<tt>-v</tt>
<br>
Sets verbose mode, in which status messages are printed about parsing and other
processing steps leading up to execution. If this option is used alone without
specifying a unit name (i.e., <tt>alf -v</tt>), then just version information is
printed.

<h3><tt>alfc</tt>: Model compilation to an Eclipse UML2 file</h3>

A model can be compiled from the command line using the <tt>alfc</tt> shell 
script (for Unix) or the <tt>alfc.bat</tt> batch file (for Windows/DOS). The output is
an Eclipse UML2 file with the same name as the compiled unit and the extension
<tt>.uml</tt> appended. All non-library imported units and subunits are resolved at 
the source level, compiled and included in the output file. References to library units,
however, are linked to already compiled UML library files.
Usage is
<p>
<tt>alfc [<em>options</em>] <em>unitName</em></tt>
<p>
where <tt><em>unitName</em></tt> is the fully qualified name of a unit to be compiled.
The allowable <tt><em>options</em></tt> are
<p>
<tt>-f</tt>
<br>
Treat the <tt><em>unitName</em></tt> as a file name, rather than as a qualified
name. The named file is expected to be found directly in the model directory
and the unit must have the same name as the file name (with any <tt>.alf</tt>
extension removed).  
<p>
<tt>-l <em>path</em></tt>
<br>
Sets the library directory location to <tt><em>path</em></tt>. If this option is not
given and the UML_LIB environment variable is set, then the value of UML_LIB is 
used as the library directory location. Otherwise, the default of <tt>UML/Libraries</tt>
is used.
<p>
<tt>-m <em>path</em></tt>
<br>
Sets the model directory location to <tt><em>path</em></tt>. Qualified name resolution to
unit file paths is relative to the root of the model directory. If this option
is not given, the default of <tt>Models</tt> is used.
<p>
<tt>-p</tt>
<br>
Parse and constraint check the identified unit, but do not generate output UML for it. This is
useful for syntactic and static semantic validation of units that are not
executable by themselves.
<p>
<tt>-P</tt>
<br>
Parse and constraint check, as for the <tt>-p</tt> option, and then print out the
resulting abstract syntax tree. Note that the print out will occur even if there
are constraint violations.
<p>
<tt>-u <em>path</em></tt>
<br>
Sets the UML output directory location to <tt><em>path</em></tt>. The output Eclipse UML2 file is
written to this directory. The file name is the unit name with <tt>.uml</tt> appended. If this
option is not given, the default of <tt>UML</tt> is used.
<p>
<tt>-v</tt>
<br>
Sets verbose mode, in which status messages are printed about parsing and other
steps in the compilation process. If this option is used alone without
specifying a unit name (i.e., <tt>alfc -v</tt>), then just version information is
printed.

<h3><tt>fuml</tt>: Model execution using Moka</h3>

An Eclipse UML2 file can be executed from the command line using the <tt>fuml</tt> shell 
script (for Unix) or the <tt>fuml.bat</tt> batch file (for Windows/DOS). The model is executed
using the Moka fUML execution engine. Usage is
<p>
<tt>fuml [<em>options</em>] <em>fileName</em></tt>
<p>
where <tt><em>fileName</em></tt> is the name of an Eclipse UML2 file to be executed. If the given
file name does not have a <tt>.uml</tt> extension, one is added.
The allowable <tt><em>options</em></tt> are
<p>
<tt>-l <em>path</em></tt>
<br>
Sets the library directory location to <tt><em>path</em></tt>. If this option is not
given and the UML_LIB environment variable is set, then the value of UML_LIB is 
used as the library directory location. Otherwise, the default of <tt>UML/Libraries</tt>
is used.
<p>
<tt>-u <em>path</em></tt>
<br>
Sets the UML input directory location to <tt><em>path</em></tt>. The Eclipse UML2 file is
expected to be found in this directory. If this option is not given, the default of <tt>UML</tt> is used.
<p>
<tt>-v</tt>
<br>
Sets verbose mode, in which status messages are printed about parsing and other
processing steps leading up to execution.


<h2>Sample Code</h2>
<p>
The installation <tt>Models</tt> directory contains a "hello world" example. A compiled version of this 
activity can be found in the installation <tt>UML</tt> directory. The distribution directory also includes zip archives
with more extensive samples, all of which are described further below.

<h3>Hello World</h3>
<p>
The <tt>Models</tt> directory included in the installation archive contains an
Alf "Hello World" activity. To run this, use the command
<p>
<tt>alf Hello</tt>
<p>
from the installation directory.
<p>
The <tt>UML</tt> directory included in the installation archive contains a compiled version of this
activity. To run this, use the command
<p>
<tt>fuml Hello</tt>
<p>
from the installation directory.
<p>
To recompile the <tt>Hello</tt> activity from Alf to UML, use the command
<p>
<tt>alfc Hello</tt>
<p>
from the installation directory.

<h3>Tests</h3>
<p>
The archive <tt>tests-x.zip</tt> unzips into a directory containing a set of simple 
execution tests. These can be run with a command of the form
<p>
<tt>alf -m tests-x <em>testUnitName</em></tt>
<p>
Replace <tt>tests-x</tt> with the complete path to the directory, as
necessary. To run with trace output, use either the <tt>-d INFO</tt> or 
<tt>-d DEBUG</tt> option.
<p>
To run the entire suite of tests, use the command
<p>
<tt>alf -m tests-x _RunTests</tt>
<p>
The archive <tt>tests-uml.zip</tt> unzips into a directory containing compiled files
for all the tests found in <tt>tests-x</tt>. These can be run with a command of the
form
<p>
<tt>fuml -u tests-uml <em>testUnitName</em></tt>
<p>
To run the entire suite of compoiled tests, use the command
<p>
<tt>fuml -u tests-uml _RunTests</tt>
<p>
A single test unit can be recompiled from Alf to UML using a command of the form
<p>
<tt>alfc -m tests-x -u tests-uml <em>testUnitName</em></tt>
<p>
Note that the <tt><em>testUnitName</em></tt> is the same as the file
name, but without the <tt>.alf</tt> extension. I.e., the file 
<tt>Expressions_Assignment.alf</tt> contains the unit named
<tt>Expressions_Assignment</tt>. However, the units <tt>_TestRunner</tt>, <tt>AssertTrue</tt>, 
<tt>AssertFalse</tt>, <tt>AssertList</tt> and <tt>Write</tt> are helper
activities, not tests, and are not individually executable.

<h3>Property Management Example</h3>
<p>
The archive <tt>PropertyManagementExample.zip</tt> unzips into a directory
containing a complete Alf implementation of the Property Management example from
Annex B.3 of the Alf specification. This example defines a service, rather than
an application. However, the activity <tt>Test</tt> sends one of each of the service
requests, printing out the results. To run this activity, use the command
<p>
<tt>alf -m PropertyManagementExample Test</tt>
<p>
Replace <tt>PropertyMangementExample</tt> with the complete path to the model
directory, as necessary.  (This should be run without trace output, 
so use the <tt>-d OFF</tt> option if the debug level default has been 
changed from <tt>OFF</tt>.)

<h3>Online Bookstore</h3>
<p>
The archive <tt>OnlineBookstore.zip</tt> unzips into a directory containing a
complete Alf implementation of the Online Bookstore example from Appendix B
of the book <em>Executable UML</em> by Stephen Mellor and Marc Balcer. It also
includes a simple textual user interface for customer interaction. By default,
the inventory consists of a single book (the <em>Executable UML</em> book,
actually). To run the application, use the command
<p>
<tt>alf -m OnlineBookstore Main</tt>
<p>
Replace <tt>OnlineBookstore</tt> with the complete path to the model directory,
as necessary. (This should be run without trace output, so use the <tt>-d OFF</tt>
option if the debug level default has been changed from <tt>OFF</tt>.)
<p>
The <tt>tests-uml</tt> archive described above also contains a compiled version
of the Online Bookstore example. To run this version, use the command
<p>
<tt>fuml -u tests-uml Main</tt>
<p>
Replace <tt>tests-uml</tt> with the complete path to the directory as necessary.
</body>
</html>
