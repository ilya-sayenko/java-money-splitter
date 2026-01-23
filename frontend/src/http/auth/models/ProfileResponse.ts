export class ProfileResponse {

  kind: string;

  users: UserResponse[];
}

class UserResponse {
  localId: string;

  email: string;

  displayName: string;
}