package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Umbra Mystic")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class UmbraMystic extends Card
{
	public static final class UmbraMysticAbility0 extends StaticAbility
	{
		public UmbraMysticAbility0(GameState state)
		{
			super(state, "Auras attached to permanents you control have totem armor.");

			SetGenerator yourAuras = Intersect.instance(HasSubType.instance(SubType.AURA), AttachedTo.instance(ControlledBy.instance(You.instance())));
			this.addEffectPart(addAbilityToObject(yourAuras, org.rnd.jmagic.abilities.keywords.TotemArmor.class));
		}
	}

	public UmbraMystic(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Auras attached to permanents you control have totem armor. (If an
		// enchanted permanent you control would be destroyed, instead remove
		// all damage from it and destroy an Aura attached to it.)
		this.addAbility(new UmbraMysticAbility0(state));
	}
}
