import {
  getGeneratorVoByIdUsingGet,
  useGeneratorUsingPost,
} from '@/services/backend/generatorController';
import { Link, useParams } from '@@/exports';
import { DownloadOutlined } from '@ant-design/icons';
import { PageContainer, ProForm } from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import {
  Button,
  Card,
  Col,
  Collapse,
  Form,
  Image,
  Input,
  message,
  Row,
  Space,
  Typography,
} from 'antd';
import { saveAs } from 'file-saver';
import React, { useEffect, useState } from 'react';
import useForm = ProForm.useForm;

/**
 * 生成器详情页
 * @constructor
 */
const GeneratorUsePage: React.FC = () => {
  const { id } = useParams();
  const [loading, setLoading] = useState<boolean>(false);
  const [data, setData] = useState<API.GeneratorVO>({});
  const { initialState } = useModel('@@initialState');
  const { currentUser } = initialState ?? {};
  const [form] = useForm();
  const models = data?.modelConfig?.models ?? [];
  const [downloading, setDownloading] = useState<boolean>(false);

  const loadData = async () => {
    if (!id) {
      return;
    }
    setLoading(true);
    try {
      const res = await getGeneratorVoByIdUsingGet({ id });
      if (res.data) {
        setData(res.data || {});
      }
    } catch (e: any) {
      message.error('获取数据失败，' + e.message);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadData();
  }, [id]);

  /**
   * 下载按钮
   */
  const downloadButton = data.distPath && currentUser && (
    <Button
      icon={<DownloadOutlined />}
      loading={downloading}
      onClick={async () => {
        setDownloading(true);
        const values = form.getFieldsValue();
        //eslint-disable-next-line react-hooks/rules-of-hooks
        const blob = await useGeneratorUsingPost(
          {
            id: data.id,
            dataModel: values,
          },
          {
            responseType: 'blob',
          },
        );
        //使用file-server来保存文件
        const fullPath = data.distPath || '';
        saveAs(blob, fullPath.substring(fullPath.lastIndexOf('/') + 1));
        setDownloading(false);
      }}
    >
      生成代码
    </Button>
  );

  return (
    <PageContainer title={<></>} loading={loading}>
      <Card>
        <Row justify="space-between" gutter={[32, 32]}>
          <Col flex="auto">
            <Space size="large" align="center">
              <Typography.Title level={4}>{data.name}</Typography.Title>
            </Space>
            <Typography.Paragraph>{data.description}</Typography.Paragraph>
            <Form form={form}></Form>
            <div style={{ marginTop: 24 }}>
              {models.map((model, index) => {
                //是分组
                if (model.groupKey) {
                  if (!model.models) {
                    return <></>;
                  }
                  return (
                    <Collapse
                      key={index}
                      style={{ marginBottom: 24 }}
                      items={[
                        {
                          key: index,
                          label: model.groupName + '(分组)',
                          children: model.models.map((subModel, index) => {
                            return (
                              <Form.Item
                                key={index}
                                label={subModel.fieldName}
                                //@ts-ignore
                                name={[model.groupKey, subModel.fieldName]}
                              >
                                <Input placeholder={subModel.description} />
                              </Form.Item>
                            );
                          }),
                        },
                      ]}
                      bordered={false}
                      defaultActiveKey={['1']}
                    />
                  );
                }
                //不是分组
                return (
                  <Form.Item key={index} label={model.fieldName} name={model.fieldName}>
                    <Input placeholder={model.description} />
                  </Form.Item>
                );
              })}
            </div>
            <Space size="middle">
              {downloadButton}
              <Link to={`/generator/detail/${id}`}>查看详情</Link>
            </Space>
          </Col>
          <Col flex="320px">
            <Image src={data.picture} />
          </Col>
        </Row>
      </Card>
    </PageContainer>
  );
};
export default GeneratorUsePage;
