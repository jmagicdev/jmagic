package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jeering Instigator")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.ROGUE})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class JeeringInstigator extends Card
{
	public static final class JeeringInstigatorAbility1 extends EventTriggeredAbility
	{
		public JeeringInstigatorAbility1(GameState state)
		{
			super(state, "When Jeering Instigator is turned face up, if it's your turn, gain control of another target creature until end of turn. Untap that creature. It gains haste until end of turn.");
			this.addPattern(whenThisIsTurnedFaceUp());
			this.interveningIf = Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance());

			Target target = this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS), "another target creature");

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect("Gain control of another target creature until end of turn.", controlPart));

			this.addEffect(untap(targetedBy(target), "Untap that creature."));

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "It gains haste until end of turn."));
		}
	}

	public JeeringInstigator(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Morph (2)(R) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(2)(R)"));

		// When Jeering Instigator is turned face up, if it's your turn, gain
		// control of another target creature until end of turn. Untap that
		// creature. It gains haste until end of turn.
		this.addAbility(new JeeringInstigatorAbility1(state));
	}
}
