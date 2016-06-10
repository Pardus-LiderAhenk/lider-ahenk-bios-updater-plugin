#!/usr/bin/python3
# -*- coding: utf-8 -*-

"""
Style Guide is PEP-8
https://www.python.org/dev/peps/pep-0008/
"""

from base.plugin.abstract_plugin import AbstractPlugin
from base.model.enum.ContentType import ContentType
from datetime import datetime

class UpdateBios(AbstractPlugin):
    def __init__(self, data, context):
        super(UpdateBios, self).__init__()
        self.data = data
        self.context = context
        self.logger = self.get_logger()
        self.message_code = self.get_message_code()

    def handle_task(self):
        print('Handling task')

        url = self.data['url']
        backup_existing = self.data['backupExisting']
        message = ""
        code = self.message_code.TASK_PROCESSED.value

        # Backup existing BIOS
        if backup_existing:
            filename = "/tmp/bios_{}.bin".format(datetime.now().strftime('%d-%m-%Y_%H:%M:%S'))
            (result_code, p_out, p_err) = self.execute("flashrom -r {} -p internal".format(filename), shell=True)
            if result_code == 0:
                message += "Varolan BIOS, {} dosyasına yedeklendi. ".format(filename)
                self.logger.debug("Existing BIOS has been read to file: {}".format(filename))
            else:
                message += "Varolan BIOS yedeklenirken hata oluştu: {} ".format(str(p_err))
                code = self.message_code.TASK_ERROR.value

        # Download BIOS file from provided URL
        (result_code, p_out, p_err) = self.execute("wget {} -O /tmp/newbios.rom".format(url), shell=True)
        if result_code == 0:
            # Flash downloaded file
            # TODO test makinasıyla test edilecek - emre
            #(result_code, p_out, p_err) = self.execute("flashrom -w /tmp/newbios.rom", shell=True)
            (result_code, p_out, p_err) = self.execute("ls -altrh /tmp/newbios.rom", shell=True)
            if result_code == 0:
                message += "BIOS başarıyla güncellendi. "
            else:
                message += "BIOS güncellenirken hata oluştu: {} ".format(str(p_err))
                code = self.message_code.TASK_ERROR.value
        else:
            message += "BIOS dosyası {} adresinden indirilirken hata oluştu: {} ".format(url, str(p_err))
            code = self.message_code.TASK_ERROR.value

        self.context.create_response(code=code, message=message,
                                     content_type=ContentType.APPLICATION_JSON.value)

def handle_task(task, context):
    print('Task Data : {}'.format(str(task)))
    handler = UpdateBios(task, context)
    handler.handle_task()
