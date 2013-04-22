package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Worldgorger Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON, SubType.NIGHTMARE})
@ManaCost("3RRR")
@Printings({@Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class WorldgorgerDragon extends Card
{
	public static final class WorldgorgerDragonAbility1 extends EventTriggeredAbility
	{
		public WorldgorgerDragonAbility1(GameState state)
		{
			super(state, "When Worldgorger Dragon enters the battlefield, exile all other permanents you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator permanentsYouControl = Intersect.instance(Permanents.instance(), ControlledBy.instance(You.instance()));
			SetGenerator otherPermanentsYouControl = RelativeComplement.instance(permanentsYouControl, ABILITY_SOURCE_OF_THIS);
			EventFactory exile = exile(otherPermanentsYouControl, "Exile all other permanents you control.");
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(WorldgorgerDragonAbility2.class);
		}
	}

	public static final class WorldgorgerDragonAbility2 extends EventTriggeredAbility
	{
		public WorldgorgerDragonAbility2(GameState state)
		{
			super(state, "When Worldgorger Dragon leaves the battlefield, return the exiled cards to the battlefield under their owners' control.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator exiledCards = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory returnCard = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Return the exiled cards to the battlefield under their owners' control.");
			returnCard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnCard.parameters.put(EventType.Parameter.OBJECT, exiledCards);
			this.addEffect(returnCard);

			this.getLinkManager().addLinkClass(WorldgorgerDragonAbility1.class);
		}
	}

	public WorldgorgerDragon(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Flying, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// When Worldgorger Dragon enters the battlefield, exile all other
		// permanents you control.
		this.addAbility(new WorldgorgerDragonAbility1(state));

		// When Worldgorger Dragon leaves the battlefield, return the exiled
		// cards to the battlefield under their owners' control.
		this.addAbility(new WorldgorgerDragonAbility2(state));
	}
}
