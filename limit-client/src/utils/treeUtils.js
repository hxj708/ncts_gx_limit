/**
 * 将平滑模式的数据转为树形结构需要的结构
 * @param data 原始数据
 * @param oldIdField 原始数据ID名称
 * @param newIdField 目标数据ID名称
 * @param oldTextField 原始数据name名称
 * @param newTextField 目标数据name名称
 * @param parentField  原始数据父ID名称
 * @param childrenField 目标数据子集合名称
 * @returns {[]|*}
 */
export function flatTreeData(data, oldIdField, newIdField, oldTextField, newTextField, parentField, childrenField) {
  if (parentField) {
    newIdField = newIdField || 'key';
    oldTextField = oldTextField || 'title';
    childrenField = childrenField || 'children';
    let i, l, treeData = [], tmpMap = [];
    for (i = 0, l = data.length; i < l; i++) {
      tmpMap[data[i][oldIdField]] = data[i];
    }
    for (i = 0, l = data.length; i < l; i++) {
      if (tmpMap[data[i][parentField]] && data[i][oldIdField] != data[i][parentField]) {
        if (!tmpMap[data[i][parentField]][childrenField]) {
          tmpMap[data[i][parentField]][childrenField] = [];
        }
        data[i][newIdField] = data[i][oldIdField];
        data[i][newTextField] = data[i][oldTextField];
        tmpMap[data[i][parentField]][childrenField].push(data[i]);
      } else {
        data[i][newIdField] = data[i][oldIdField];
        data[i][newTextField] = data[i][oldTextField];
        treeData.push(data[i]);
      }
    }
    return treeData;
  }
  return data;
}
