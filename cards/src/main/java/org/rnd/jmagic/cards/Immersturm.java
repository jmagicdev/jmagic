package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Immersturm")
@Types({Type.PLANE})
@SubTypes({SubType.VALLA})
@Printings({@Printings.Printed(ex = org.rnd.jmagic.expansions.Planechase.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Immersturm extends Card
{
	public static final class Pandemonium extends EventTriggeredAbility
	{
		public Pandemonium(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield, that creature's controller may have it deal damage equal to its power to target creature or player of his or her choice.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), CreaturePermanents.instance(), false));

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator thatCreaturesController = ControllerOf.instance(thatCreature);

			Target target = new Target(CREATURES_AND_PLAYERS, thatCreaturesController, "target creature or player");
			this.addTarget(target);

			EventFactory factory = permanentDealDamage(PowerOf.instance(thatCreature), targetedBy(target), "It deals damage equal to its power to target creature or player of his or her choice.");
			this.addEffect(playerMay(thatCreaturesController, factory, "That creature's controller may have it deal damage equal to its power to target creature or player of his or her choice."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public static final class MomentaryChaos extends EventTriggeredAbility
	{
		public MomentaryChaos(GameState state)
		{
			super(state, "Whenever you roll (C), exile target creature, then return it to the battlefield under its owner's control.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			EventType.ParameterMap blinkParameters = new EventType.ParameterMap();
			blinkParameters.put(EventType.Parameter.CAUSE, This.instance());
			blinkParameters.put(EventType.Parameter.TARGET, targetedBy(target));
			blinkParameters.put(EventType.Parameter.PLAYER, OwnerOf.instance(targetedBy(target)));
			this.addEffect(new EventFactory(BLINK, blinkParameters, "Exile target creature, then return it to the battlefield under its owner's control."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Immersturm(GameState state)
	{
		super(state);

		this.addAbility(new Pandemonium(state));

		this.addAbility(new MomentaryChaos(state));
	}
}
