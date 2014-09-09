package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Neurok Stealthsuit")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({Color.BLUE})
public final class NeurokStealthsuit extends Card
{
	public static final class NeurokStealthsuitAbility0 extends StaticAbility
	{
		public NeurokStealthsuitAbility0(GameState state)
		{
			super(state, "Equipped creature has shroud.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Shroud.class));
		}
	}

	public static final class NeurokStealthsuitAbility1 extends ActivatedAbility
	{
		public NeurokStealthsuitAbility1(GameState state)
		{
			super(state, "(U)(U): Attach Neurok Stealthsuit to target creature you control.");
			this.setManaCost(new ManaPool("(U)(U)"));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			this.addEffect(attach(ABILITY_SOURCE_OF_THIS, target, "Attach Neurok Stealthsuit to target creature you control."));
		}
	}

	public NeurokStealthsuit(GameState state)
	{
		super(state);

		// Equipped creature has shroud. (It can't be the target of spells or
		// abilities.)
		this.addAbility(new NeurokStealthsuitAbility0(state));

		// (U)(U): Attach Neurok Stealthsuit to target creature you control.
		this.addAbility(new NeurokStealthsuitAbility1(state));

		// Equip (1) ((1): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
