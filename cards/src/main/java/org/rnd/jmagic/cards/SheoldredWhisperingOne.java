package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sheoldred, Whispering One")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.PRAETOR})
@ManaCost("5BB")
@ColorIdentity({Color.BLACK})
public final class SheoldredWhisperingOne extends Card
{
	public static final class SheoldredWhisperingOneAbility1 extends EventTriggeredAbility
	{
		public SheoldredWhisperingOneAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, return target creature card from your graveyard to the battlefield.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator creatureCards = HasType.instance(Type.CREATURE);
			SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
			SetGenerator targeting = Intersect.instance(creatureCards, inYourGraveyard);

			SetGenerator target = targetedBy(this.addTarget(targeting, "target creature card from your graveyard"));
			EventFactory returnToBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return target creature card from your graveyard to the battlefield.");
			returnToBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnToBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			returnToBattlefield.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(returnToBattlefield);

		}
	}

	public static final class SheoldredWhisperingOneAbility2 extends EventTriggeredAbility
	{
		public SheoldredWhisperingOneAbility2(GameState state)
		{
			super(state, "At the beginning of each opponent's upkeep, that player sacrifices a creature.");
			this.addPattern(atTheBeginningOfEachOpponentsUpkeeps());

			SetGenerator thatPlayer = OwnerOf.instance(CurrentStep.instance());
			this.addEffect(sacrifice(thatPlayer, 1, CreaturePermanents.instance(), "That player sacrifices a creature."));
		}
	}

	public SheoldredWhisperingOne(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Swampwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));

		// At the beginning of your upkeep, return target creature card from
		// your graveyard to the battlefield.
		this.addAbility(new SheoldredWhisperingOneAbility1(state));

		// At the beginning of each opponent's upkeep, that player sacrifices a
		// creature.
		this.addAbility(new SheoldredWhisperingOneAbility2(state));
	}
}
