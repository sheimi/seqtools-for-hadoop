#!/usr/bin/env python
"""
seqtools.py
~~~~~~~
This file contains shell wrapper for seqtools.jar

:copyright: (c) 2012 by sheimi.
:license: BSD, see LISENSE for more details.

"""

from os import path
import argparse
import subprocess


class SeqToolShell(object):

    def __init__(self, args, parser):
        self.args = args
        self.parser = parser
        self.cmd_tpl = [
            'java', '-cp',
            path.dirname(path.abspath(__file__)) + '/seqtools.jar',
        ]

    def __call__(self):
        if self.args.m is None:
            self.__exit()
        return getattr(self, self.args.m)()

    def __exit(self):
        self.parser.print_help()
        exit(1)

    def __src_des(self, main_class):
        src, des = self.args.s, self.args.d
        if src is None or des is None:
            self.__exit()
        cmd = self.cmd_tpl + [main_class, src, des]
        subprocess.call(cmd)

    def seq_to_tar(self):
        self.__src_des('me.sheimi.hadoop.seq.SeqFileToTar')

    def tar_to_seq(self):
        self.__src_des('me.sheimi.hadoop.seq.TarToSeqFile')


if __name__ == '__main__':
    parser = argparse.ArgumentParser()

    parser.add_argument('-m')
    parser.add_argument('-s')
    parser.add_argument('-d')

    args = parser.parse_args()
    SeqToolShell(args, parser)()
