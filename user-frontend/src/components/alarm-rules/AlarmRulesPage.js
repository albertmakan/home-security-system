import { useEffect, useState } from 'react';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import { toastSuccessMessage } from '../../toast/toastMessages';
import AlarmRuleService from '../../services/AlarmRuleService';
import NewAlarmRuleModal from '../modals/NewAlarmRuleModal';

const AlarmRulePage = () => {
  const [alarmRules, setAlarmRules] = useState([]);
  const [show, setShow] = useState(false);

  useEffect(() => {
    AlarmRuleService.getAll().then((ar) => setAlarmRules(ar));
  }, []);

  const handleCreate = (ruleForm) => {
    ruleForm.conditions.forEach((c) => {
      if (!isNaN(c.value)) {
        c.value = +c.value;
      }
    });
    AlarmRuleService.create(ruleForm).then((ar) => {
      toastSuccessMessage('Alarm rule added');
      setAlarmRules([...alarmRules, ar]);
    });
    console.log(ruleForm);
  };

  const handleRemove = (alarmRule) => {
    if (!window.confirm(`Do you want to delete alarm rule '${alarmRule.id}?'`)) return;
    AlarmRuleService.remove(alarmRule.id).then(() => {
      toastSuccessMessage('Alarm rule removed');
      setAlarmRules(alarmRules.filter((a) => a.id !== alarmRule.id));
    });
  };

  const ops = { EQ: '==', NEQ: '!=', GT: '>', LT: '<', GTE: '>=', LTE: '<=' };

  return (
    <>
      <h3>Alarm rules</h3>
      <Table>
        <thead>
          <tr>
            <th>Device type</th>
            <th>Conditions</th>
            <th>Alarm text</th>
          </tr>
        </thead>
        <tbody>
          {alarmRules.map((ar) => (
            <tr key={ar.id}>
              <td>{ar.deviceType}</td>
              <td>
                {ar.conditions.map((c) => `${c.field} ${ops[c.operator]} ${c.value}`).join(', ')}
              </td>
              <td>{ar.alarmText}</td>
              <td>
                <Button variant="danger" size="sm" onClick={() => handleRemove(ar)}>
                  x
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <Button variant="primary" onClick={() => setShow(true)}>
        Add alarm rule
      </Button>
      <NewAlarmRuleModal show={show} onClose={() => setShow(false)} onCreate={handleCreate} />
    </>
  );
};

export default AlarmRulePage;
