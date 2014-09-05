package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ichorid")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Torment.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Ichorid extends Card
{
	public static final class IchoridAbility1 extends EventTriggeredAbility
	{
		public IchoridAbility1(GameState state)
		{
			super(state, "At the beginning of the end step, sacrifice Ichorid.");
			this.addPattern(atTheBeginningOfTheEndStep());
			this.addEffect(sacrificeThis("Ichorid"));
		}
	}

	public static final class IchoridAbility2 extends EventTriggeredAbility
	{
		public IchoridAbility2(GameState state)
		{
			super(state, "At the beginning of your upkeep, if Ichorid is in your graveyard, you may exile a black creature card other than Ichorid from your graveyard. If you do, return Ichorid to the battlefield.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.canTrigger = ABILITY_SOURCE_IS_IN_GRAVEYARD;
			this.interveningIf = ABILITY_SOURCE_IS_IN_GRAVEYARD;

			this.addEffect(ifThen(youMay(exile(You.instance(), RelativeComplement.instance(Intersect.instance(HasColor.instance(Color.BLACK), HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), ABILITY_SOURCE_OF_THIS), 1, "Exile a black creature card other than Ichorid from your graveyard."), "You may exile a black creature card other than Ichorid from your graveyard."), putOntoBattlefield(ABILITY_SOURCE_OF_THIS, "Return Ichorid to the battlefield."), "You may exile a black creature card other than Ichorid from your graveyard. If you do, return Ichorid to the battlefield."));
		}
	}

	public Ichorid(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// At the beginning of the end step, sacrifice Ichorid.
		this.addAbility(new IchoridAbility1(state));

		// At the beginning of your upkeep, if Ichorid is in your graveyard, you
		// may exile a black creature card other than Ichorid from your
		// graveyard. If you do, return Ichorid to the battlefield.
		this.addAbility(new IchoridAbility2(state));
	}
}
