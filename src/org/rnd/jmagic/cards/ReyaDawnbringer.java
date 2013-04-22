package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reya Dawnbringer")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("6WWW")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class ReyaDawnbringer extends Card
{
	public static final class DailyPrayers extends EventTriggeredAbility
	{
		public DailyPrayers(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may return target creature card from your graveyard to the battlefield.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			Target target = this.addTarget(Intersect.instance(InZone.instance(GraveyardOf.instance(You.instance())), HasType.instance(Type.CREATURE)), "target creature card from your graveyard");

			EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return target creature card from your graveyard to the battlefield");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			move.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));

			this.addEffect(youMay(move, "You may return target creature card from your graveyard to the battlefield."));
		}
	}

	public ReyaDawnbringer(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(6);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new DailyPrayers(state));
	}
}
