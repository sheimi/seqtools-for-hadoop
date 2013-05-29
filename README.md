SEQ TOOLS
=========

Overview
--------
It is a sequence file tool for our project.

Depencency
----------
    ant.jar                       commons-lang-2.4.jar          hadoop-core-1.0.3.jar         log4j-1.2.15.jar
    protobuf-java-2.4.0a.jar      slf4j-log4j12-1.4.3.jar       commons-configuration-1.6.jar commons-logging-1.1.1.jar
    hbase-0.94.1.jar              pig-0.10.0.jar                slf4j-api-1.4.3.jar           zookeeper-3.4.3.jar

Examples
--------

### Convert Image Storage Format

    bin/seqtools.py -m convert -s A.tar B.seq
    bin/seqtools.py -m convert -s A.seq B.tar

### Pig Latin

    register seqtools.jar                            
    a = LOAD '/test'  USING me.sheimi.pig.storage.SequenceFileLoader AS (name, data);                                    
    b = foreach a generate name, me.sheimi.pig.eval.BytesNativeEvalFunc(data, '/tmp/ug-web/shell-env/0/test/cvcv.so', 'cvcv');
    store b into '/output' using me.sheimi.pig.storage.SequenceFileStorage;

    c = foreach a generate name,
    me.sheimi.pig.eval.BytesStringNativeEvalFunc(data, '/tmp/ug-web/shell-env/0/test/cv_histogram.so', 'histogram');
    dump c
