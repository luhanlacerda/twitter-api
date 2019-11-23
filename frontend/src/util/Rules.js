import validator from 'validator';

export default {
  required: value =>
    !validator.isEmpty(String(value || '')) || 'Campo obrigatório',
  email: value => validator.isEmail(String(value || '')) || 'E-mail inválido',
  integer: value =>
    validator.isInt(String(value || ''), {
      min: -9223372036854775808,
      max: 9223372036854775807
    }) || 'Valor inválido para Inteiro',
  numeric: value =>
    validator.isNumeric(String(value || '')) || 'Número inválido',
  ip: value => validator.isNIP(String(value || '')) || 'IP inválido',
  integerOptional: value => {
    if (value) {
      return (
        validator.isInt(String(value || ''), {
          min: -9223372036854775808,
          max: 9223372036854775807
        }) || 'Valor inválido para Inteiro'
      );
    }
    return true;
  }
};
