package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Akroan Conscriptor")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class AkroanConscriptor extends Card
{
	public static final class AkroanConscriptorAbility0 extends EventTriggeredAbility
	{
		public AkroanConscriptorAbility0(GameState state)
		{
			super(state, "Whenever you cast a spell that targets Akroan Conscriptor, gain control of another target creature until end of turn. Untap that creature. It gains haste until end of turn.");
			this.addPattern(heroic());

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			Target target = this.addTarget(anotherCreature, "another target creature");

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect("Gain control of target creature until end of turn.", controlPart));

			this.addEffect(untap(targetedBy(target), "Untap that creature."));

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "It gains haste until end of turn."));
		}
	}

	public AkroanConscriptor(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Whenever you cast a spell that targets Akroan Conscriptor,
		// gain control of another target creature until end of turn. Untap that
		// creature. It gains haste until end of turn.
		this.addAbility(new AkroanConscriptorAbility0(state));
	}
}
