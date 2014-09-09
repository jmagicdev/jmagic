package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elbrus, the Binding Blade")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("7")
@ColorIdentity({})
@BackFace(WithengarUnbound.class)
public final class ElbrustheBindingBlade extends Card
{
	public static final class ElbrustheBindingBladeAbility0 extends StaticAbility
	{
		public ElbrustheBindingBladeAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+0.");

			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), 1, 0));
		}
	}

	public static final class ElbrustheBindingBladeAbility1 extends EventTriggeredAbility
	{
		public ElbrustheBindingBladeAbility1(GameState state)
		{
			super(state, "When equipped creature deals combat damage to a player, unattach Elbrus, the Binding Blade, then transform it.");

			this.addPattern(whenDealsCombatDamageToAPlayer(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));

			EventFactory factory = new EventFactory(EventType.UNATTACH, "Unattach Elbrus, the Binding Blade.");
			factory.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(factory);

			this.addEffect(transform(ABILITY_SOURCE_OF_THIS, "Transform it."));
		}
	}

	public ElbrustheBindingBlade(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+0.
		this.addAbility(new ElbrustheBindingBladeAbility0(state));

		// When equipped creature deals combat damage to a player, unattach
		// Elbrus, the Binding Blade, then transform it.
		this.addAbility(new ElbrustheBindingBladeAbility1(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
