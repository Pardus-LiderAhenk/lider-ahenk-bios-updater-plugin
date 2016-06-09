#!/usr/bin/python3
# -*- coding: utf-8 -*-

"""
Style Guide is PEP-8
https://www.python.org/dev/peps/pep-0008/
"""

from base.plugin.abstract_plugin import AbstractPlugin
from base.model.enum.ContentType import ContentType

class ReadBiosInfo(AbstractPlugin):
    def __init__(self, data, context):
        super(ReadBiosInfo, self).__init__()
        self.data = data
        self.context = context
        self.logger = self.get_logger()
        self.message_code = self.get_message_code()

    def handle_task(self):
        print('Handling task')
        data = {}
        # BIOS info
        (result_code, p_out, p_err) = self.execute("dmidecode --string bios-vendor", shell=True)
        if result_code == 0:
            data['bios-vendor'] = str(p_out)
        else:
            self.logger.debug('Error occurred while retrieving bios-vendor')
        (result_code, p_out, p_err) = self.execute("dmidecode --string bios-version", shell=True)
        if result_code == 0:
            data['bios-version'] = str(p_out)
        else:
            self.logger.debug('Error occurred while retrieving bios-version')
        (result_code, p_out, p_err) = self.execute("dmidecode --string bios-release-date", shell=True)
        if result_code == 0:
            data['bios-release-date'] = str(p_out)
        else:
            self.logger.debug('Error occurred while retrieving bios-release-date')
        # Motherboard info
        (result_code, p_out, p_err) = self.execute("dmidecode --string baseboard-manufacturer", shell=True)
        if result_code == 0:
            data['baseboard-manufacturer'] = str(p_out)
        else:
            self.logger.debug('Error occurred while retrieving baseboard-manufacturer')
        (result_code, p_out, p_err) = self.execute("dmidecode --string baseboard-product-name", shell=True)
        if result_code == 0:
            data['baseboard-product-name'] = str(p_out)
        else:
            self.logger.debug('Error occurred while retrieving baseboard-product-name')
        (result_code, p_out, p_err) = self.execute("dmidecode --string baseboard-version", shell=True)
        if result_code == 0:
            data['baseboard-version'] = str(p_out)
        else:
            self.logger.debug('Error occurred while retrieving baseboard-version')
        (result_code, p_out, p_err) = self.execute("dmidecode --string baseboard-serial-number", shell=True)
        if result_code == 0:
            data['baseboard-serial-number'] = str(p_out)
        else:
            self.logger.debug('Error occurred while retrieving baseboard-serial-number')
        (result_code, p_out, p_err) = self.execute("dmidecode --string baseboard-asset-tag", shell=True)
        if result_code == 0:
            data['baseboard-asset-tag'] = str(p_out)
        else:
            self.logger.debug('Error occurred while retrieving baseboard-asset-tag')

        # Resulting data is empty! Task failed.
        code = self.message_code.TASK_PROCESSED.value
        message = 'BIOS bilgileri okundu'
        if not bool(data):
            code = self.message_code.TASK_ERROR.value
            message = 'BIOS bilgileri okunurken hata oluştu.'

        self.context.create_response(code=code, message=message,
                                     data=data, content_type=ContentType.APPLICATION_JSON.value)

def handle_task(task, context):
    print('Task Data : {}'.format(str(task)))
    handler = ReadBiosInfo(task, context)
    handler.handle_task()
