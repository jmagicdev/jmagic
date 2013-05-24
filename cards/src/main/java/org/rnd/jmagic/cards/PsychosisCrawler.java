package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Psychosis Crawler")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.HORROR})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({})
public final class PsychosisCrawler extends Card
{
	public static final class PsychosisCrawlerAbility0 extends CharacteristicDefiningAbility
	{
		public PsychosisCrawlerAbility0(GameState state)
		{
			super(state, "Psychosis Crawler's power and toughness are each equal to the number of cards in your hand.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator number = Count.instance(InZone.instance(HandOf.instance(You.instance())));
			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public static final class PsychosisCrawlerAbility1 extends EventTriggeredAbility
	{
		public PsychosisCrawlerAbility1(GameState state)
		{
			super(state, "Whenever you draw a card, each opponent loses 1 life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(pattern);

			this.addEffect(loseLife(OpponentsOf.instance(You.instance()), 1, "Each opponent loses 1 life."));
		}
	}

	public PsychosisCrawler(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Psychosis Crawler's power and toughness are each equal to the number
		// of cards in your hand.
		this.addAbility(new PsychosisCrawlerAbility0(state));

		// Whenever you draw a card, each opponent loses 1 life.
		this.addAbility(new PsychosisCrawlerAbility1(state));
	}
}
