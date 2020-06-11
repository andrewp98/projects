# Library Management Supplementary Specification

# Introduction
The purpose of this document is to define requirements of the library management system. This Supplementary Specification lists the requirements that are not readily captured in the use cases of the use-case model. The Supplementary Specifications and the use-case model together capture a complete set of requirements on the system.

# Non-functional Requirements

[Define system quality attributes in terms of scenarios according to the following template:
-	Quality attribute definition
-	Source of stimulus: the entity (human or another system) that generated the stimulus or event
-	Stimulus: a condition that determines a reaction of the system
-	Environment: the current condition of the system when the stimulus arrives
-	Artifact: is a component that reacts to the stimulus. It may be the whole system or some pieces of it
-	Response: the activity determined by the arrival of the stimulus
-	Response measure: the quantifiable indication of the response
-	Tactics]
## Availability

1. This section lists all reliability requirements.

2. 1. **Availability**

   2. The Library Management system shall be available only when the library is open.

   3. **Mean Time Between Failures**

      Mean Time Between Failures shall exceed 300 hours.

## Performance

1. The performance characteristics of the system are outlined in this section.

2. 1. **Simultaneous Users**

   2. The system shall support up to 200 simultaneous users against the central database at any given time, and up to 30 simultaneous users against the local servers at any one time.

   3. **Database Access Response Time**

   4. The system shall provide access to the legacy course catalog database with no more than a 10 second latency.

   5. **Transaction Response Time**

      The system must be able to complete 80% of all transactions within 2 minutes.

## Security

​			The system's ability to resist to unauthorized usage (attack) represents application's level of security.

​			One administrator can try to log in and modify user rights, return date and time, borrowed books or delete reports. The system should block the operations made by unauthorized administrators.

## Testability

​			The system's response with expected results to an specific input from a tester.

## Usability

​			This section lists all of those requirements that relate to, or affect, the usability of the system.

1. **Windows Compliance**
2. The desktop user-interface shall be Windows 10 compliant.

3. **Design for Ease-of-Use**
4. The user interface of the Library Management system shall be designed for ease-of-use and shall be appropriate for a computer-literate user community with no additional training on the System.

# Design Constraints
[This section needs to indicate any design constraints on the system being built. Design constraints represent design decisions that have been mandated and must be adhered to. Examples include software languages, software process requirements, prescribed use of developmental tools, architectural and design constraints, purchased components, class libraries, and so on.]

1. This section lists any design constraints on the system being built.

2. 1. **Course Catalog Legacy System**
   2. The system shall integrate with existing legacy system (course catalog database) which operates on the Library Main Frame.

   3. **Billing System**
   4. The Library Management System shall interface with the existing Course Billing System which operates on the Library Main Frame.

   5. **Platform Requirements**
   6. The client portion of the Library Management System shall operate on any personal computer with a PENTIUM III processor or greater. The client portion shall require less than 250 MB disk space and 128 MB RAM.

   7. The server portion of the Library Management System shall operate on the Library's  server.

   8. **Internet Browsers**
   9. The web-based interface for the Library Management System shall run in any 2020 updated browser.

   10. **Java Compatibility**
   11. The web-based interface shall be compatible with the Java SE 13 VM runtime environment.

# Resources

* http://www.upedu.org/process/gdlines/md_srs.htm
* Example of Supplementary Specification: http://csis.pace.edu/~marchese/SE616_New/Samples/Example%20%20Supplementary%20Specification.htm
