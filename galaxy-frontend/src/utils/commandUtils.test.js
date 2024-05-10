import { validateEmail } from "./commanUtils";

test("valide", function () {
    
    expect(validateEmail("johndoe@ex.com")).toBe(true);
});