#!/usr/bin/python3
# -*- coding: utf-8 -*-

"""
Style Guide is PEP-8
https://www.python.org/dev/peps/pep-0008/
"""

from base.plugin.abstract_plugin import AbstractPlugin
from base.model.enum.ContentType import ContentType

class UpdateBios(AbstractPlugin):
    def __init__(self, data, context):
        super(UpdateBios, self).__init__()
        self.data = data
        self.context = context
        self.logger = self.get_logger()
        self.message_code = self.get_message_code()

    def handle_task(self):
        print('Handling task')


def handle_task(task, context):
    print('Task Data : {}'.format(str(task)))
    handler = UpdateBios(task, context)
    handler.handle_task()
