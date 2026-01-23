import type {ProfileResponse} from "@/http/auth/models/ProfileResponse.ts";

export class UserMapper {
  static fromProfileResponse(response: ProfileResponse) {
    const userResponse = response.users[0];
    return {
      id: userResponse!.localId,
      name: userResponse!.displayName,
      email: userResponse!.email
    }
  }
}