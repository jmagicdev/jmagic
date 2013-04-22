package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ringskipper")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.FAERIE})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Ringskipper extends Card
{
	public static final class RingskipperAbility1 extends EventTriggeredAbility
	{
		public RingskipperAbility1(GameState state)
		{
			super(state, "When Ringskipper dies, clash with an opponent. If you win, return Ringskipper to its owner's hand.");
			this.addPattern(whenThisDies());

			EventFactory clash = new EventFactory(EventType.CLASH_WITH_AN_OPPONENT, "Clash with an opponent.");
			clash.parameters.put(EventType.Parameter.CAUSE, This.instance());
			clash.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(clash);

			EventFactory returnFactory = new EventFactory(EventType.MOVE_OBJECTS, "Return Ringskipper to its owner's hand.");
			returnFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnFactory.parameters.put(EventType.Parameter.TO, HandOf.instance(OwnerOf.instance(ABILITY_SOURCE_OF_THIS)));
			returnFactory.parameters.put(EventType.Parameter.OBJECT, FutureSelf.instance(ABILITY_SOURCE_OF_THIS));

			EventFactory ifThen = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If you win, return Ringskipper to its owner's hand.");
			ifThen.parameters.put(EventType.Parameter.IF, Intersect.instance(You.instance(), EffectResult.instance(clash)));
			ifThen.parameters.put(EventType.Parameter.THEN, Identity.instance(returnFactory));
			this.addEffect(ifThen);
		}
	}

	public Ringskipper(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Ringskipper is put into a graveyard from the battlefield, clash
		// with an opponent. If you win, return Ringskipper to its owner's hand.
		this.addAbility(new RingskipperAbility1(state));
	}
}
