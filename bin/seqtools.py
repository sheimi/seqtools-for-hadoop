#!/usr/bin/env python
"""
seqtools.py
~~~~~~~
This file contains shell wrapper for seqtools.jar

:copyright: (c) 2012 by sheimi.
:license: BSD, see LISENSE for more details.

"""

from os import path
import os
import argparse
import subprocess


class SeqToolShell(object):

    def __init__(self, args, parser):
        self.args = args
        self.parser = parser

        home_dir = path.dirname(path.dirname(path.abspath(__file__)))
        classpaths = [
            '/bin/seqtools.jar', '/conf',
        ]
        classpaths.extend([
            '/lib/' + jar
            for jar in os.listdir(home_dir + '/lib')
            if  jar.endswith(".jar")
        ])
        classpaths = [home_dir + cp for cp in classpaths]
        self.cmd_tpl = [
            'java', '-cp',
            ':'.join(classpaths),
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
        print ' '.join(cmd)
        subprocess.call(cmd)

    def convert(self):
        self.__src_des('me.sheimi.magic.image.ImageConvertor')

    def meta_json(self):
        param = self.args.p
        cmd = self.cmd_tpl + ['me.sheimi.magic.image.meta.MetaLoader', param]
        subprocess.call(cmd)

    def hbase_client(self):
        cmd = self.cmd_tpl + ['me.sheimi.hbase.ClientTest']
        subprocess.call(cmd)

    def hbase_image_schema(self):
        param = self.args.p[0]
        cmd = self.cmd_tpl + ['me.sheimi.hbase.image.ImageSchema', param]
        subprocess.call(cmd)

    def archive_image(self):
        param = self.args.p
        cmd = self.cmd_tpl + ['me.sheimi.hbase.image.ArchiveImage'] + param
        subprocess.call(cmd)

    def retrieve_image(self):
        param = self.args.p
        cmd = self.cmd_tpl + ['me.sheimi.hbase.image.RetrieveImage'] + param
        subprocess.call(cmd)


if __name__ == '__main__':
    parser = argparse.ArgumentParser()

    parser.add_argument('-m')
    parser.add_argument('-s')
    parser.add_argument('-d')
    parser.add_argument('-p', nargs='+')

    args = parser.parse_args()
    SeqToolShell(args, parser)()
