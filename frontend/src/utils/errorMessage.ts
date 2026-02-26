import type {ErrorObject} from "@vuelidate/core";

export function errorMessage(errors: ErrorObject[]) {
  if (errors.length > 0) {
    return errors[0] ? errors[0].$message : '';
  }

  return '';
}