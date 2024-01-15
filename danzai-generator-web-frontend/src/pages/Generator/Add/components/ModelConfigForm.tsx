import { CloseOutlined } from '@ant-design/icons';
import { Button, Card, Form, FormListFieldData, Input, Space } from 'antd';

interface Props {
  formRef: any;
  oldData: any;
}

export default (props: Props) => {
  const { formRef, oldData } = props;
  /**
   * 单个字段视图
   * @param field
   * @param remove
   */
  const singleFieldFormView = (
    field: FormListFieldData,
    remove?: (index: number | number[]) => void,
  ) => {
    return (
      <Space key={field.key}>
        <Form.Item name={[field.name, 'name']} label="字段名称">
          <Input />
        </Form.Item>
        <Form.Item name={[field.name, 'description']} label="描述">
          <Input />
        </Form.Item>
        <Form.Item name={[field.name, 'defaultValue']} label="默认值">
          <Input />
        </Form.Item>
        <Form.Item name={[field.name, 'type']} label="类型">
          <Input />
        </Form.Item>
        <Form.Item name={[field.name, 'abbr']} label="缩写">
          <Input />
        </Form.Item>
        {remove && (
          <Button
            danger
            onClick={() => {
              remove(field.name);
            }}
          >
            删除
          </Button>
        )}
      </Space>
    );
  };

  return (
    <Form.List name={['modelConfig', 'models']}>
      {(fields, { add, remove }) => (
        <div style={{ display: 'flex', rowGap: 16, flexDirection: 'column' }}>
          {fields.map((field) => {
            const modelConfig =
              formRef?.current?.getFieldsValue()?.modelConfig ?? oldData?.modelConfig;
            const groukey = modelConfig?.models?.[field.name]?.groupKey;
            return (
              <Card
                size="small"
                title={groukey ? '分组' : '未分组字段'}
                key={field.key}
                extra={
                  <CloseOutlined
                    onClick={() => {
                      remove(field.name);
                    }}
                  />
                }
              >
                {groukey ? (
                  <Space>
                    <Form.Item name={[field.name, 'groupKey']} label="分组key">
                      <Input />
                    </Form.Item>
                    <Form.Item name={[field.name, 'groupName']} label="分组名称">
                      <Input />
                    </Form.Item>
                    <Form.Item name={[field.name, 'type']} label="类型">
                      <Input />
                    </Form.Item>
                    <Form.Item name={[field.name, 'condition']} label="条件">
                      <Input />
                    </Form.Item>
                  </Space>
                ) : (
                  singleFieldFormView(field)
                )}
                {/*  组内字段*/}
                {groukey && (
                  <Form.Item label="组内字段">
                    <Form.List name={[field.name, 'models']}>
                      {(subFields, subOpt) => (
                        <div style={{ display: 'flex', flexDirection: 'column', rowGap: 16 }}>
                          {subFields.map((subField) =>
                            singleFieldFormView(subField, subOpt.remove),
                          )}
                          <Button type="dashed" onClick={() => subOpt.add()} block>
                            添加组内字段
                          </Button>
                        </div>
                      )}
                    </Form.List>
                  </Form.Item>
                )}
              </Card>
            );
          })}
          <Button type="dashed" onClick={() => add()}>
            添加字段
          </Button>
          <Button
            type="dashed"
            onClick={() =>
              add({
                groupName: '分组名',
                groupKey: 'group',
              })
            }
          >
            添加分组
          </Button>
        </div>
      )}
    </Form.List>
  );
};
