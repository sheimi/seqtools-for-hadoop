SEQ TOOLS
=========

Overview
--------
It is a sequence file tool for our project.

Depencency
----------
    ant.jar                       commons-lang-2.4.jar          hadoop-core-1.0.1.jar         pig-0.9.2.jar
    commons-configuration-1.6.jar commons-logging-1.1.jar       log4j-1.2.14.jar

Examples
--------

### SeqToTar
    
    java -cp seqtools.jar me.sheimi.hadoop.seq.SeqFileToTar A B

### TarToSeq

    java -cp seqtools.jar me.sheimi.hadoop.seq.TarToSeqFile A B

### Pig Latin

    register seqtools.jar;
    a = LOAD '/seq_test'  USING me.sheimi.pig.storage.SequenceFileLoader AS (name, data);
    b = foreach a generate name;
    -- Java
    c = foreach a generate name, me.sheimi.pig.eval.demo.ToBMP(data);
    -- Native (CPP and OpenCV)
    -- should copy libcvjni.so to java.library.path
    c = foreach a generate name, me.sheimi.pig.eval.BytesNativeEvalFunc(data);
    store c into '/output' using me.sheimi.pig.storage.SequenceFileStorage;
