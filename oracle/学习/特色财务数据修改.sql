select t.*, t.rowid from shxg_qyxg_��ɫ������� t;

update shxg_qyxg_��ɫ������� t
set t.����1��ֵ_Ԫ = round(to_number(replace(t.����1��ֵ_Ԫ,',','')),2)
, t.����2��ֵ_Ԫ = round(to_number(replace(t.����2��ֵ_Ԫ,',','')),2)
, t.����3��ֵ_Ԫ = round(to_number(replace(t.����3��ֵ_Ԫ,',','')),2)
, t.����4��ֵ_Ԫ = round(to_number(replace(t.����4��ֵ_Ԫ,',','')),2)
, t.����5��ֵ_Ԫ = round(to_number(replace(t.����5��ֵ_Ԫ,',','')),2)
, t.����6��ֵ_Ԫ = round(to_number(replace(t.����6��ֵ_Ԫ,',','')),2)
, t.��λ��Ծ�û���ֵ_Ԫ_�� = round(to_number(replace(t.��λ��Ծ�û���ֵ_Ԫ_��,',','')),2)
, t.��λӪ��Ч��_�û���_Ԫ = round(to_number(replace(t.��λӪ��Ч��_�û���_Ԫ,',','')),2)
, t.�ۺ�ָ�� = round(to_number(replace(t.�ۺ�ָ��,',','')),2)
, t.�з�����_Ԫ = round(to_number(replace(t.�з�����_Ԫ,',','')),2)
, t.�Կͻ���ǿ��ָ��_Ԫ = round(to_number(replace(t.�Կͻ���ǿ��ָ��_Ԫ,',','')),2)
, t.�Թ�Ӧ�̵�ǿ��ָ��_Ԫ = round(to_number(replace(t.�Թ�Ӧ�̵�ǿ��ָ��_Ԫ,',','')),2)
, t.Ӫҵ�ɱ�_Ԫ = round(to_number(replace(t.Ӫҵ�ɱ�_Ԫ,',','')),2)
, t.Ӫҵ˰�𼰸���_Ԫ = round(to_number(replace(t.Ӫҵ˰�𼰸���_Ԫ,',','')),2)
, t.���۷���_Ԫ = round(to_number(replace(t.���۷���_Ԫ,',','')),2)
, t.Ӧ��Ʊ��_Ԫ = round(to_number(replace(t.Ӧ��Ʊ��_Ԫ,',','')),2)
, t.Ӧ���˿�_Ԫ = round(to_number(replace(t.Ӧ���˿�_Ԫ,',','')),2)
, t.Ԥ���˿�_Ԫ = round(to_number(replace(t.Ԥ���˿�_Ԫ,',','')),2)
, t.Ӧ��Ʊ��_Ԫ = round(to_number(replace(t.Ӧ��Ʊ��_Ԫ,',','')),2)
, t.Ӧ���˿�_Ԫ = round(to_number(replace(t.Ӧ���˿�_Ԫ,',','')),2)
, t.Ԥ���˿�_Ԫ = round(to_number(replace(t.Ԥ���˿�_Ԫ,',','')),2);
