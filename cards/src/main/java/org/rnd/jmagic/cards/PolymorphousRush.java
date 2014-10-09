package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Polymorphous Rush")
@Types({Type.INSTANT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class PolymorphousRush extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("PolymorphousRush", "Choose what to copy.", true);

	public PolymorphousRush(GameState state)
	{
		super(state);

		// Strive \u2014 Polymorphous Rush costs (1)(U) more to cast for each
		// target beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(1)(U)"));

		// Choose a creature on the battlefield.
		EventFactory choose = playerChoose(You.instance(), 1, CreaturePermanents.instance(), PlayerInterface.ChoiceType.OBJECTS, REASON, "Choose a creature on the battlefield.");
		this.addEffect(choose);
		SetGenerator chosen = EffectResult.instance(choose);

		// Any number of target creatures you control each become a copy of that
		// creature until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "any number of target creatures you control").setNumber(0, null));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
		part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, chosen);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
		this.addEffect(createFloatingEffect("Any number of target creatures you control each become a copy of that creature until end of turn.", part));
	}
}
