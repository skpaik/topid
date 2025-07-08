import { ReactNode } from "react";
import {signIn, signOut, useSession} from "next-auth/react"
import {
  Box,
  Avatar,
  Button,
  HStack,
  VStack,
  Image,
  Input,
  Spacer,
  Menu,
  MenuButton,
  MenuList,
  MenuItem,
  Text,
  Link,
  MenuDivider
} from "@chakra-ui/react";
import Container from "./container";
type IconButtonProps = {
  children: ReactNode;
};

const IconButton = ({ children }: IconButtonProps) => {
  return (
    <Button
      padding="0.4rem"
      width="auto"
      height="auto"
      borderRadius="100%"
      bg="transparent"
      _hover={{ bg: "#f6f6f6" }}
    >
      {children}
    </Button>
  );
};

const Navbar = () => {
    const { data: session, status } = useSession()
  return (
    <Box
      py="2"
      boxShadow="sm"
      border="0 solid #e5e7eb"
      position="fixed"
      top="0"
      bg="#fff"
      width="100%"
      zIndex="1"
    >
      <Container>
        <HStack spacing={4}>
          <Image src="/assets/images/logo.svg" />
          <Button
                        borderRadius="4px"
                        _hover={{bg: "#323ebe"}}>
                        U Trip
          </Button>
          <Input
            maxW="26rem"
            placeholder="Search..."
            borderColor="#b5bdc4"
            borderRadius="5px"
            d={{ base: "none", md: "block" }}
          />
          <Spacer />
          <HStack spacing={3}>
            <Button
              color="#fff"
              borderRadius="4px"
              bg="#3b49df"
              _hover={{ bg: "#323ebe" }}
            >
              Create a post
            </Button>
            <IconButton>
              <Image src="/assets/images/notification.svg" />
            </IconButton>
            <IconButton>
              <Image src="/assets/images/bell.svg" />
            </IconButton>
              <Menu isLazy>
           <MenuList
                                zIndex={5}
                                border="2px solid"
                                borderColor="gray.700"
                                boxShadow="4px 4px 0"
                              >
                                <MenuItem>
                                    <Text fontWeight="500">Dashboard</Text>
                                </MenuItem>
                                <MenuItem>
                                    <Text fontWeight="500">Create Post</Text>
                                </MenuItem>
                                <MenuItem>
                                    <Text fontWeight="500">Reading List</Text>
                                </MenuItem>
                                <MenuItem>
                                    <Text fontWeight="500">Settings</Text>
                                </MenuItem>
                                <MenuDivider/>
                                <MenuItem>
                                    <Text fontWeight="500">See all</Text>
                                </MenuItem>
                            </MenuList>
            </Menu>
            <Menu isLazy>
              <MenuButton as={Button} size="sm" px={0} py={0} rounded="full">
                  {session?.user && (
                      <Avatar
                          size={"sm"}
                          src={session.user.image}
                      />
                  )}
              </MenuButton>
              <MenuList
                zIndex={5}
                border="2px solid"
                borderColor="gray.700"
                boxShadow="4px 4px 0"

              >
                  {session?.user && (
                      <>
                          <Link
                              href="/account"
                              _hover={{textDecoration: "bold"}}
                              isExternal={false}
                          >
                              <MenuItem>
                                  <VStack justify="start" alignItems="left">
                                      <Text fontWeight="500">
                                          {session.user.name}
                                      </Text>
                                      <Text size="sm" color="gray.500" mt="0 !important">
                                          {session.user.email}
                                      </Text>
                                  </VStack>
                              </MenuItem>
                          </Link>
                          <MenuDivider/>
                      </>
                  )}
                  <MenuItem>
                      <Link
                          href="/dashboard"
                          _hover={{textDecoration: "none"}}
                      >
                          <Text fontWeight="500">Dashboard</Text>
                      </Link>
                  </MenuItem>
                  <MenuItem>
                      <Text fontWeight="500">Reading List</Text>
                  </MenuItem>
                <MenuItem>
                  <Text fontWeight="500">Settings</Text>
                </MenuItem>
                <MenuDivider />
                  {!session && (
                      <MenuItem>
                          <a
                              href="/api/auth/signin"
                              onClick={(e) => {
                                  e.preventDefault()
                                  signIn()
                              }}
                          >
                              Sign In
                          </a>
                      </MenuItem>
                  )}
                  {session?.user && (
                      <MenuItem>
                          <a
                              href="/api/auth/signout"
                              onClick={(e) => {
                                  e.preventDefault()
                                  signOut()
                              }}
                          >
                              Sign Out
                          </a>
                      </MenuItem>
                  )}
              </MenuList>
            </Menu>
          </HStack>
        </HStack>
      </Container>
    </Box>
  );
};

export default Navbar;
