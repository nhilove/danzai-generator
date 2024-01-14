import { COS_HOST } from '@/constants';
import { uploadFileUsingPost } from '@/services/backend/fileController';
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import type { UploadProps } from 'antd';
import { message, Upload } from 'antd';
import React, { useState } from 'react';

/**
 * 图片
 */
interface Props {
  biz: string;
  onChange?: (url: string) => void;
  value?: string;
}

/**
 * 图片上传组件
 * @param props
 * @constructor
 */
const PictureUploader: React.FC<Props> = (props) => {
  const { biz, value, onChange } = props;
  const [loading, setLoading] = useState<boolean>(false);

  const uploadProps: UploadProps = {
    name: 'file',
    multiple: false,
    listType: 'picture-card',
    showUploadList: false,
    maxCount: 1,
    customRequest: async (fileObj: any) => {
      setLoading(true);
      try {
        const res = await uploadFileUsingPost({ biz }, {}, fileObj.file);
        //拼接图片的完整路径
        const fullPath = COS_HOST + res.data;
        onChange?.(fullPath ?? '');
        fileObj.onSuccess(res.data);
      } catch (e: any) {
        message.error('上传失败,' + e.message);
        fileObj.onError(e);
      }
      setLoading(false);
    },
  };

  const uploadButton = (
    <div>
      {loading ? <LoadingOutlined /> : <PlusOutlined />}
      <div style={{ marginTop: 8 }}>上传</div>
    </div>
  );
  // @ts-ignore
  return (
    <Upload {...uploadProps}>
      {value ? <img src={value} alt="picture" style={{ width: '100%' }} /> : uploadButton}
    </Upload>
  );
};

export default PictureUploader;
