package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Captivating Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class CaptivatingVampire extends Card
{
	public static final class CaptivatingVampireAbility1 extends ActivatedAbility
	{
		public CaptivatingVampireAbility1(GameState state)
		{
			super(state, "Tap five untapped Vampires you control: Gain control of target creature. It becomes a Vampire in addition to its other types.");
			// Tap five untapped Vampires you control
			EventFactory factory = new EventFactory(EventType.TAP_CHOICE, "Tap five untapped Vampires you control");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.VAMPIRE)), Untapped.instance()));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(5));
			this.addCost(factory);

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			ContinuousEffect.Part typePart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			typePart.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			typePart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.VAMPIRE));

			this.addEffect(createFloatingEffect("Gain control of target creature.", controlPart));
		}
	}

	public CaptivatingVampire(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other Vampire creatures you control get +1/+1.
		SetGenerator yourOtherVampires = RelativeComplement.instance(Intersect.instance(HasSubType.instance(SubType.VAMPIRE), CREATURES_YOU_CONTROL), This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, yourOtherVampires, "Other Vampire creatures you control", +1, +1, true));

		// Tap five untapped Vampires you control: Gain control of target
		// creature. It becomes a Vampire in addition to its other types.
		this.addAbility(new CaptivatingVampireAbility1(state));
	}
}
