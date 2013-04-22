package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Kukemssa Pirates")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.PIRATE})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class KukemssaPirates extends Card
{
	public static final class StealArtifact extends EventTriggeredAbility
	{
		public StealArtifact(GameState state)
		{
			super(state, "Whenever Kukemssa Pirates attacks and isn't blocked, you may gain control of target artifact defending player controls. If you do, Kukemssa Pirates assigns no combat damage this turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_UNBLOCKED);
			pattern.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			SetGenerator defendingPlayer = DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS);
			Target target = this.addTarget(Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(defendingPlayer)), "target artifact defending player controls");

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			EventFactory changeControl = createFloatingEffect(Empty.instance(), "Gain control of target artifact defending player controls", controlPart);

			EventFactory youMayGainControl = youMay(changeControl, "You may gain control of target artifact defending player controls.");

			SimpleEventPattern thisAssignsDamage = new SimpleEventPattern(EventType.ASSIGN_COMBAT_DAMAGE);
			thisAssignsDamage.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			EventReplacementEffect replacement = new EventReplacementEffect(this.game, "Kukemssa Pirates assigns no combat damage", thisAssignsDamage);

			EventFactory assignNoDamage = new EventFactory(EventType.ASSIGN_COMBAT_DAMAGE, "Kukemssa Pirates assigns no combat damage");
			assignNoDamage.parameters.put(EventType.Parameter.NUMBER, numberGenerator(0));
			replacement.addEffect(assignNoDamage);

			EventFactory thisAssignsNoDamage = createFloatingReplacement(replacement, "Kukemssa Pirates assigns no combat damage this turn");

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may gain control of target artifact defending player controls. If you do, Kukemssa Pirates assigns no combat damage this turn.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(youMayGainControl));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(thisAssignsNoDamage));
			this.addEffect(effect);
		}
	}

	public KukemssaPirates(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Kukemssa Pirates attacks and isn't blocked, you may gain
		// control of target artifact defending player controls. If you do,
		// Kukemssa Pirates assigns no combat damage this turn.
		this.addAbility(new StealArtifact(state));
	}
}
