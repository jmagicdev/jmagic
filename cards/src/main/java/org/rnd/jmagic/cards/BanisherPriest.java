package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Banisher Priest")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Magic2014CoreSet.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class BanisherPriest extends Card
{
	public static class ETBExile extends EventTriggeredAbility
	{
		public ETBExile(GameState state)
		{
			super(state, "When Banisher Priest enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			
			SetGenerator opponentsCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			Target target = this.addTarget(opponentsCreatures, "target creature an opponent controls");

			state.ensureTracker(new LeftTheBattlefield.LeavesTheBattlefieldTracker());
			SetGenerator thisIsGone = Intersect.instance(ABILITY_SOURCE_OF_THIS, LeftTheBattlefield.instance());

			EventFactory exileUntil = new EventFactory(EventType.EXILE_UNTIL, "Exile target creature an opponent controls until Banisher Priest leaves the battlefield.");
			exileUntil.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileUntil.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			exileUntil.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(thisIsGone));
			this.addEffect(exileUntil);
		}
	}

	public BanisherPriest(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new ETBExile(state));
	}
}
