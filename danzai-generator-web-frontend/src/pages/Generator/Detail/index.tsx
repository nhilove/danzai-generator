import AuthorInfo from '@/pages/Generator/Detail/components/AuthorInfo';
import {
  downloadGeneratorByIdUsingGet,
  getGeneratorVoByIdUsingGet,
} from '@/services/backend/generatorController';
import { Link, useParams } from '@@/exports';
import { DownloadOutlined, EditOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import { Button, Card, Col, message, Row, Space, Tabs, Tag, Typography } from 'antd';
import { saveAs } from 'file-saver';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import FileConfig from './components/FileConfig';
import ModelConfig from './components/ModelConfig';

/**
 * 生成器详情页
 * @constructor
 */
const GeneratorDetailPage: React.FC = () => {
  const { id } = useParams();
  const [loading, setLoading] = useState<boolean>(true);
  const [data, setData] = useState<API.GeneratorVO>({});
  const { initialState } = useModel('@@initialState');
  const { currentUser } = initialState ?? {};
  const my = currentUser?.id === data?.userId;

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
   * 标签视图
   * @param tags
   */
  const tagListView = (tags?: string[]) => {
    if (!tags) {
      return <></>;
    }
    return (
      <div style={{ marginBottom: 8 }}>
        {tags.map((tag) => (
          <Tag key={tag}>{tag}</Tag>
        ))}
      </div>
    );
  };
  /**
   * 下载按钮
   */
  const downloadButton = data.distPath && currentUser && (
    <Button
      icon={<DownloadOutlined />}
      onClick={async () => {
        const blob = await downloadGeneratorByIdUsingGet(
          {
            id: data.id,
          },
          {
            responseType: 'blob',
          },
        );
        //使用file-server来保存文件
        const fullPath = data.distPath || '';
        saveAs(blob, fullPath.substring(fullPath.lastIndexOf('/') + 1));
      }}
    >
      下载
    </Button>
  );

  /**
   *  编辑按钮
   */
  const editButton = my && (
    <Link to={`/generator/update?id=${data.id}`}>
      <Button icon={<EditOutlined />}>编辑</Button>
    </Link>
  );
  return (
    <PageContainer title={<></>} loading={loading}>
      <Card>
        <Row justify="space-between" gutter={[32, 32]}>
          <Col flex="auto">
            <Space size="large" align="center">
              <Typography.Title level={4}>{data.name}</Typography.Title>
              {tagListView(data.tags)}
            </Space>
            <Typography.Paragraph>{data.description}</Typography.Paragraph>
            <Typography.Paragraph type="secondary">
              创建时间: {moment(data.createTime).format('YYYY-MM-DD hh:mm:ss')}
            </Typography.Paragraph>
            <Typography.Paragraph type="secondary">基础包: {data.basePackage}</Typography.Paragraph>
            <Typography.Paragraph type="secondary">版本: {data.version}</Typography.Paragraph>
            <Typography.Paragraph type="secondary">作者: {data.author}</Typography.Paragraph>
            <div style={{ marginTop: 24 }}></div>
            <Space size="middle">
              <Button type="primary">立即使用</Button>
              {downloadButton}
              {editButton}
            </Space>
          </Col>
          <Col flex="320px">
            <img src={data.picture} />
          </Col>
        </Row>
      </Card>
      <div style={{ marginTop: 24 }}></div>
      <Card>
        <Tabs
          size="large"
          defaultActiveKey="fileConfig"
          items={[
            { key: 'fileConfig', label: '文件配置', children: <FileConfig data={data} /> },
            { key: 'modelConfig', label: '模型配置', children: <ModelConfig data={data} /> },
            { key: 'userInfo', label: '作者信息', children: <AuthorInfo data={data} /> },
          ]}
          onChange={() => {}}
        />
      </Card>
    </PageContainer>
  );
};
export default GeneratorDetailPage;
