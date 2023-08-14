import {
    Box,
    Heading,
    Spacer,
    Button,
    VStack,
    HStack,
    Grid,
    Text,
    Link,
    Image,
    Wrap
} from '@chakra-ui/react'

function Card({
                  title,
                  bodyHtml,
                  userFullName,
                  userProfileImageUrl,
                  publishedDate,
                  tagList,
                  headerImage,
                  postId,
                  readingTime,
                  reactionCount,
                  commentCount,
              }) {
    return (
        <Box
            mt="3"
            as="article"
            bg="white"
            borderRadius="md"
            overflow="hidden"
            border="1px solid #08090a1a"
        >
            {headerImage ? <Image src={headerImage}/> : ''}
            <Grid
                templateColumns={{base: '1fr'}}
                gap={2}
                p={5}
            >
                <Box>
                    <HStack
                        spacing={3}
                        d={{base: 'none', sm: 'flex'}}>
                        <Image
                            alt="user profile"
                            src={userProfileImageUrl}
                            w="8"
                            borderRadius="full"
                        />
                        <Text color="#4d5760" fontSize="14px" fontWeight="500">
                            {userFullName}
                        </Text>
                        <Text color="#4d5760" fontSize="12px">
                            {publishedDate}
                        </Text>
                    </HStack>
                    <Heading fontSize={headerImage ? '30px' : '24px'} mt="3">
                        <Link
                            href={`/v/${postId}`}
                            _hover={{color: '#323ebe', textDecoration: 'none'}}
                        >
                            {title}
                        </Link>
                    </Heading>
                    <Wrap mt="3" fontSize="14px" color="#64707d">
                        <span dangerouslySetInnerHTML={{__html: bodyHtml}}/>
                    </Wrap>
                    <HStack mt="3" fontSize="14px" color="#64707d">
                        {tagList.map((tag, idx) => (
                            <Text as={Link} key={idx}>
                                #{tag}
                            </Text>
                        ))}
                    </HStack>
                    <HStack mt={3}>
                        <Button
                            leftIcon={<Image src="/assets/images/like.svg"/>}
                            ml={-2}
                            bg="transparent"
                            padding="6px 8px"
                            height="auto"
                            fontWeight="normal"
                            fontSize="14px"
                            lineHeight="1.2"
                            borderRadius="4px"
                            _hover={{bg: '#f6f6f6'}}
                        >
                            {reactionCount}
                            <Box ml="2" as="span" d={{base: 'none', sm: 'block'}}>
                                reactions
                            </Box>
                        </Button>
                        <Button
                            leftIcon={<Image src="/assets/images/comment.svg"/>}
                            bg="transparent"
                            padding="6px 8px"
                            height="auto"
                            fontWeight="normal"
                            fontSize="14px"
                            lineHeight="1.2"
                            borderRadius="4px"
                            _hover={{bg: '#f6f6f6'}}
                        >
                            {commentCount}
                            <Box ml="2" as="span" d={{base: 'none', sm: 'block'}}>
                                comments
                            </Box>
                        </Button>
                        <Spacer/>
                        <Text fontSize="12px">{readingTime} min read</Text>
                        <Button
                            bg="#d2d6db"
                            padding="8px 12px"
                            height="auto"
                            fontWeight="normal"
                            fontSize="14px"
                            _hover={{bg: '#b5bdc4'}}
                        >
                            Save
                        </Button>
                    </HStack>
                </Box>
            </Grid>
        </Box>
    )
}

const PostCard = ({post_details, tag_list = [], idx = 0}) => {
    if (tag_list && tag_list.length === 0) {
        if (post_details.tags || Array.isArray(post_details.tags)) {
            tag_list = post_details.tags;
        } else if (post_details.tag_list || Array.isArray(post_details.tag_list)) {
            tag_list = post_details.tag_list;
        }
    }

    let user_full_name = '';
    let user_profile_image_url = '';

    if (post_details.user) {
        user_full_name = post_details.user.name;
        user_profile_image_url = post_details.user ? post_details.user.profile_image_url ? post_details.user.profile_image_url : post_details.user.profile_image ? post_details.user.profile_image : '' : '';
    }

    return (
        <Card
            key={post_details.id}
            title={post_details.title}
            bodyHtml={post_details.body_html}
            userFullName={user_full_name}
            userProfileImageUrl={user_profile_image_url}
            tagList={tag_list}
            readingTime={post_details.reading_time}
            commentCount={post_details.comments_count}
            reactionCount={post_details.public_reactions_count}
            postId={post_details.id}
            publishedDate={post_details.readable_publish_date}
            headerImage={idx === 0 ? post_details.main_image ? post_details.main_image : post_details.cover_image ? post_details.cover_image : '' : ''}
        />
    )
}

export default PostCard
