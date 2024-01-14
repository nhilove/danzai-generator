import { COS_HOST } from '@/constants';
import {
  testDownloadFileUsingGet,
  testUploadFileUsingPost,
} from '@/services/backend/fileController';
import { InboxOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-components';
import type { UploadProps } from 'antd';
import { Button, Card, Divider, Flex, message, Upload } from 'antd';
import Paragraph from 'antd/es/typography/Paragraph';
import { saveAs } from 'file-saver';
import React, { useState } from 'react';

const { Dragger } = Upload;

/**
 * 文件测试
 */

const TestFilePage: React.FC = () => {
  const [value, setValue] = useState<string>();

  const props: UploadProps = {
    name: 'file',
    multiple: false,
    maxCount: 1,
    customRequest: async (fileObj: any) => {
      try {
        const res = await testUploadFileUsingPost({}, fileObj.file);
        fileObj.onSuccess(res.data);
        setValue(res.data);
      } catch (e: any) {
        message.error('上传失败,' + e.message);
        fileObj.onError(e);
      }
    },
    onRemove() {
      setValue(undefined);
    },
  };

  // @ts-ignore
  return (
    <PageContainer>
      <Flex gap={16}>
        <Card title="文件上传">
          <Dragger {...props}>
            <p className="ant-upload-drag-icon">
              <InboxOutlined />
            </p>
            <p className="ant-upload-text">Click or drag file to this area to upload</p>
            <p className="ant-upload-hint">
              Support for a single or bulk upload. Strictly prohibited from uploading company data
              or other banned files.
            </p>
          </Dragger>
        </Card>
        <Card title="文件下载" loading={!value}>
          <div>
            文件地址:<Paragraph copyable>{COS_HOST + value}</Paragraph>
          </div>
          <img src={COS_HOST + value} height={280} />
          <Divider />
          <Button
            onClick={async () => {
              const blob = await testDownloadFileUsingGet(
                {
                  filepath: value,
                },
                {
                  responseType: 'blob',
                },
              );
              //使用file-server 来保存文件
              const fullPath = COS_HOST + value;
              saveAs(blob, fullPath.substring(fullPath.lastIndexOf('/') + 1));
            }}
          >
            点击下载
          </Button>
        </Card>
      </Flex>
    </PageContainer>
  );
};

export default TestFilePage;
