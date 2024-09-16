package pl.yndtask.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.yndtask.model.ExitNodeInfo;
import pl.yndtask.service.ExitNodeParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExitNodeParserTest {

    private ExitNodeParser exitNodeParser;

    @BeforeEach
    void setUp() {
        exitNodeParser = new ExitNodeParser();
    }

    @Test
    void shouldParseValidInput() {
       //given
        String input = """
                ExitNode 64D74AAA74F30DC2CFB36343CE5D4451B9A4DBA8
                Published 2024-09-11 04:01:53
                LastStatus 2024-09-11 17:00:00
                ExitAddress 171.25.193.25 2024-09-11 17:13:42
                """;

        //when
        List<ExitNodeInfo> result = exitNodeParser.parseString(input);

        //then
        assertEquals(1, result.size());
        ExitNodeInfo parsedInfo = result.get(0);
        assertEquals("64D74AAA74F30DC2CFB36343CE5D4451B9A4DBA8", parsedInfo.getExitNode());
        assertEquals("2024-09-11 04:01:53", parsedInfo.getPublished());
        assertEquals("2024-09-11 17:00:00", parsedInfo.getLastStatus());
        assertEquals("171.25.193.25", parsedInfo.getExitAddress());
        assertEquals("2024-09-11 17:13:42", parsedInfo.getExitAddressTime());
    }

    @Test
    void shouldReturnEmptyListForEmptyInput() {
        //when
        List<ExitNodeInfo> result = exitNodeParser.parseString("");

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldParseMultipleExitNodes() {
        //given
        String input = """
                ExitNode 64D74AAA74F30DC2CFB36343CE5D4451B9A4DBA8
                Published 2024-09-11 04:01:53
                LastStatus 2024-09-11 17:00:00
                ExitAddress 171.25.193.25 2024-09-11 17:13:42
                            
                ExitNode A7D74BBB44G31EF2CFB36343CE5D4451B9A4DBA9
                Published 2024-09-12 05:02:54
                LastStatus 2024-09-12 18:01:01
                ExitAddress 192.42.116.187 2024-09-12 18:14:43
                """;

        //when
        List<ExitNodeInfo> result = exitNodeParser.parseString(input);

        //then
        assertEquals(2, result.size());

        ExitNodeInfo firstNode = result.get(0);
        assertEquals("64D74AAA74F30DC2CFB36343CE5D4451B9A4DBA8", firstNode.getExitNode());
        assertEquals("171.25.193.25", firstNode.getExitAddress());

        ExitNodeInfo secondNode = result.get(1);
        assertEquals("A7D74BBB44G31EF2CFB36343CE5D4451B9A4DBA9", secondNode.getExitNode());
        assertEquals("192.42.116.187", secondNode.getExitAddress());
    }
}
