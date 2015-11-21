package com.playtika.cards.service;

import com.playtika.cards.domain.Album;
import com.playtika.cards.domain.AlbumSet;
import com.playtika.cards.domain.Card;
import com.playtika.cards.domain.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.playtika.cards.domain.Event.Type.ALBUM_FINISHED;
import static com.playtika.cards.domain.Event.Type.SET_FINISHED;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.stream.Collectors.toList;
import static java.util.stream.LongStream.range;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.mockito.Mockito.when;

/**
 * Created by rostyslavs on 11/21/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class CardAssignerTest {

    @Mock
    private ConfigurationProvider configurationProvider;

    private final List<Long> users = range(0L, 10L).boxed()
            .collect(toList());

    private CardAssigner cardAssigner = new DefaultCardAssigner();

    @Before
    public void configure() {
        when(configurationProvider.get()).thenReturn(new Album(1L, "Animals", newHashSet(
                new AlbumSet(1L, "Birds", newHashSet(new Card(1L, "Eagle"), new Card(2L, "Cormorant"), new Card(3L, "Sparrow"), new Card(4L, "Raven"))),
                new AlbumSet(2L, "Fish", newHashSet(new Card(5L, "Salmon"), new Card(6L, "Mullet"), new Card(7L, "Bream"), new Card(8L, "Marline")))
        )));
    }

    @Test(timeout = 20000L)
    public void assigningCardsToUsers() {
        final List<Event> events = newArrayList();
        cardAssigner.subscribe(event -> events.add(event));

        Album album = configurationProvider.get();
        ExecutorService executorService = newFixedThreadPool(10);
        final List<Card> allCards = album.sets.stream().map(set -> set.cards).flatMap(Collection::stream).collect(toList());
        while (!albumsFinished(events)) {
            executorService.submit(() -> {
                Card card = allCards.get(nextInt(0, allCards.size()));
                Long userId = users.get(nextInt(0, users.size()));
                cardAssigner.assignCard(userId, card.id);
            });
        }
        assert events.stream().filter(event -> event.type == ALBUM_FINISHED).count() == users.size();
        assert events.stream().filter(event -> event.type == SET_FINISHED).count() == users.size() * album.sets.size();
    }

    private boolean albumsFinished(List<Event> events) {
        return events.stream().filter(event -> event.type == ALBUM_FINISHED).count() >= users.size();
    }
}