import FileUpLoader from '@/components/FileUploader';
import PictureUploader from '@/components/PictureUploader';
import { COS_HOST } from '@/constants';
import {
  addGeneratorUsingPost,
  editGeneratorUsingPost,
  getGeneratorVoByIdUsingGet,
} from '@/services/backend/generatorController';
import { useSearchParams } from '@@/exports';
import {
  ProCard,
  ProFormInstance,
  ProFormItem,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
  StepsForm,
} from '@ant-design/pro-components';
import { history } from '@umijs/max';
import { message, UploadFile } from 'antd';
import React, { useEffect, useRef, useState } from 'react';

/**
 * 生成器制作页
 * @constructor
 */
const GeneratorAddPage: React.FC = () => {
  const [searchParams] = useSearchParams();
  const id = searchParams.get('id');
  const [oldData, setOldData] = useState<API.GeneratorEditRequest>();
  const formRef = useRef<ProFormInstance>();

  const loadData = async () => {
    if (!id) {
      return;
    }
    try {
      const res = await getGeneratorVoByIdUsingGet({ id });
      if (res.data) {
        const { distPath } = res.data ?? {};
        if (distPath) {
          //@ts-ignore
          res.data.distPath = [
            {
              uid: id,
              name: '文件' + id,
              status: 'done',
              url: COS_HOST + distPath,
              response: distPath,
            } as UploadFile,
          ];
          setOldData(res.data);
        }
      }
    } catch (e: any) {
      message.error('加载数据失败，' + e.message);
    }
  };

  useEffect(() => {
    if (id) {
      loadData();
    }
  }, [id]);

  const doAdd = async (values: API.GeneratorAddRequest) => {
    //调用接口
    try {
      const res = await addGeneratorUsingPost(values);
      if (res.data) {
        message.success('创建成功');
        history.push(`/generator/detail/${res.data}`);
      }
    } catch (e: any) {
      message.error('创建失败，' + e.message);
    }
  };

  const doUpdate = async (values: API.GeneratorEditRequest) => {
    //调用接口
    try {
      const res = await editGeneratorUsingPost(values);
      if (res.data) {
        message.success('更新成功');
        history.push(`/generator/detail/${res.data}`);
      }
    } catch (e: any) {
      message.error('更新失败，' + e.message);
    }
  };

  const doSubmit = async (values: API.GeneratorAddRequest) => {
    //参数处理
    if (!values.fileConfig) {
      values.fileConfig = {};
    }
    if (!values.modelConfig) {
      values.modelConfig = {};
    }
    if (values.distPath && values.distPath.length > 0) {
      //@ts-ignore
      values.distPath = values.distPath[0].response;
    }
    //调用接口
    if (id) {
      await doUpdate({
        id,
        ...values,
      });
    } else {
      await doAdd({
        ...values,
      });
    }
  };

  return (
    <ProCard>
      {(!id || oldData) && (
        <StepsForm<API.GeneratorAddRequest>
          formRef={formRef}
          onFinish={doSubmit}
          formProps={{
            initialValues: oldData,
          }}
        >
          <StepsForm.StepForm
            name="base"
            title="基本信息"
            onFinish={async () => {
              return true;
            }}
          >
            <ProFormText name="name" label="名称" placeholder="请输入名称" />
            <ProFormTextArea name="description" label="描述" placeholder="请输入描述" />
            <ProFormText name="basePackage" label="基础包" placeholder="请输入基础包" />
            <ProFormText name="version" label="版本" placeholder="请输入版本" />
            <ProFormText name="author" label="作者" placeholder="请输入作者名" />
            <ProFormSelect label="标签" name="tags" mode="tags" placeholder="请输入标签列表" />
            <ProFormItem label="图片" name="picture">
              <PictureUploader biz="generator_picture" />
            </ProFormItem>
          </StepsForm.StepForm>
          <StepsForm.StepForm name="fileConfig" title="文件配置"></StepsForm.StepForm>
          <StepsForm.StepForm name="modelConfig" title="模型配置"></StepsForm.StepForm>
          <StepsForm.StepForm name="dist" title="生成器文件">
            <ProFormItem label="产物包" name="distPath">
              <FileUpLoader biz="generator_dist" description="请上传生成器代码压缩包" />
            </ProFormItem>
          </StepsForm.StepForm>
        </StepsForm>
      )}
    </ProCard>
  );
};
export default GeneratorAddPage;
