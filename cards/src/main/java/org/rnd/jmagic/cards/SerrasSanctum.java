package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Serra's Sanctum")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SerrasSanctum extends Card
{
	public static final class SerrasSanctumAbility0 extends ActivatedAbility
	{
		public SerrasSanctumAbility0(GameState state)
		{
			super(state, "(T): Add (W) to your mana pool for each enchantment you control.");
			this.costsTap = true;

			SetGenerator numControllersEnchantments = Count.instance(Intersect.instance(HasType.instance(Type.ENCHANTMENT), ControlledBy.instance(You.instance())));

			EventFactory effect = new EventFactory(EventType.ADD_MANA, "Add (W) to your mana pool for each enchantment you control.");
			effect.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			effect.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("W")));
			effect.parameters.put(EventType.Parameter.NUMBER, numControllersEnchantments);
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(effect);
		}
	}

	public SerrasSanctum(GameState state)
	{
		super(state);

		// (T): Add (W) to your mana pool for each enchantment you control.
		this.addAbility(new SerrasSanctumAbility0(state));
	}
}
