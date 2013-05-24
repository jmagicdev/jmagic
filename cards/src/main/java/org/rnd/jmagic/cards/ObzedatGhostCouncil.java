package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Obzedat, Ghost Council")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ADVISOR, SubType.SPIRIT})
@ManaCost("1WWBB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class ObzedatGhostCouncil extends Card
{
	public static final class ObzedatGhostCouncilAbility0 extends EventTriggeredAbility
	{
		public ObzedatGhostCouncilAbility0(GameState state)
		{
			super(state, "When Obzedat, Ghost Council enters the battlefield, target opponent loses 2 life and you gain 2 life.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(loseLife(target, 2, "Target opponent loses 2 life"));
			this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));
		}
	}

	public static final class ObzedatGhostCouncilAbility1 extends EventTriggeredAbility
	{
		public ObzedatGhostCouncilAbility1(GameState state)
		{
			super(state, "At the beginning of your end step, you may exile Obzedat. If you do, return it to the battlefield under its owner's control at the beginning of your next upkeep. It gains haste.");
			this.addPattern(atTheBeginningOfYourEndStep());

			EventFactory exile = exile(ABILITY_SOURCE_OF_THIS, "Exile Obzedat");

			SetGenerator inExile = NewObjectOf.instance(delayedTriggerContext(EffectResult.instance(exile)));
			EventFactory returnToBattlefield = putOntoBattlefield(inExile, "Return it to the battlefield");

			SetGenerator onBattlefield = NewObjectOf.instance(EffectResult.instance(returnToBattlefield));
			ContinuousEffect.Part giveHastePart = addAbilityToObject(onBattlefield, org.rnd.jmagic.abilities.keywords.Haste.class);
			EventFactory giveHaste = createFloatingEffect(Empty.instance(), "It gains haste", giveHastePart);

			EventFactory returnWithHaste = sequence(returnToBattlefield, giveHaste);

			EventFactory returnWithHasteAtUpkeep = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Return it to the battlefield under its owner's control at the beginning of your next upkeep. It gains haste.");
			returnWithHasteAtUpkeep.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnWithHasteAtUpkeep.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfYourUpkeep()));
			returnWithHasteAtUpkeep.parameters.put(EventType.Parameter.EFFECT, Identity.instance(returnWithHaste));

			this.addEffect(ifThen(youMay(exile), returnWithHasteAtUpkeep, "You may exile Obzedat. If you do, return it to the battlefield under its owner's control at the beginning of your next upkeep. It gains haste."));
		}
	}

	public ObzedatGhostCouncil(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// When Obzedat, Ghost Council enters the battlefield, target opponent
		// loses 2 life and you gain 2 life.
		this.addAbility(new ObzedatGhostCouncilAbility0(state));

		// At the beginning of your end step, you may exile Obzedat. If you do,
		// return it to the battlefield under its owner's control at the
		// beginning of your next upkeep. It gains haste.
		this.addAbility(new ObzedatGhostCouncilAbility1(state));
	}
}
