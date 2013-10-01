biblehistoriale-website contains two properties that must be configured:

* bible.index - this is where the Solr index will be placed (or has already been placed). This is a relative path, relative to the biblehistoriale-website webapp directory. The default value is usually fine.
* bible.store - this is the location of the MS profiles. This is an absolute path. The default value is /mnt/cis

These properties can be configured by either changing their values in the pom.xml, or through the command line.

To configure the values in the command line, while building with Maven, add -Dproperty.name="value"
EX in windows: mvn package -Dbible.store="C:\\Users\\john\\BibleHistoriale\\Example bibles"