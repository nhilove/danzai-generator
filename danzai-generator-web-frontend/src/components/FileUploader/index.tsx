import { uploadFileUsingPost } from '@/services/backend/fileController';
import { InboxOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-components';
import type { UploadFile, UploadProps } from 'antd';
import { message, Upload } from 'antd';
import React, { useState } from 'react';

const { Dragger } = Upload;

/**
 * 文件
 */
interface Props {
  biz: string;
  onChange?: (fileList: UploadFile[]) => void;
  value?: UploadFile[];
  description?: string;
}

/**
 * 文件上传组件
 * @param props
 * @constructor
 */
const FileUpLoader: React.FC<Props> = (props) => {
  const { biz, value, description, onChange } = props;
  const [loading, setLoading] = useState<boolean>(false);

  const uploadProps: UploadProps = {
    name: 'file',
    multiple: false,
    fileList: value,
    disabled: loading,
    maxCount: 1,
    onChange: ({ fileList }) => {
      onChange?.(fileList);
    },
    customRequest: async (fileObj: any) => {
      setLoading(true);
      try {
        const res = await uploadFileUsingPost({ biz }, {}, fileObj.file);
        fileObj.onSuccess(res.data);
      } catch (e: any) {
        message.error('上传失败,' + e.message);
        fileObj.onError(e);
      }
      setLoading(false);
    },
  };

  // @ts-ignore
  return (
    <PageContainer>
      <Dragger {...uploadProps}>
        <p className="ant-upload-drag-icon">
          <InboxOutlined />
        </p>
        <p className="ant-upload-text">点击上传拖拽文件</p>
        <p className="ant-upload-hint">{description}</p>
      </Dragger>
    </PageContainer>
  );
};

export default FileUpLoader;
