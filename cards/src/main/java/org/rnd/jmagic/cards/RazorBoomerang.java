package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Razor Boomerang")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class RazorBoomerang extends Card
{
	public static final class ThrowMe extends ActivatedAbility
	{
		public ThrowMe(GameState state)
		{
			super(state, "(T), Unattach Razor Boomerang: Razor Boomerang deals 1 damage to target creature or player. Return Razor Boomerang to its owner's hand.");
			this.costsTap = true;

			SetGenerator razorBoomerang = Granted.instance(This.instance());

			EventFactory unattach = new EventFactory(EventType.UNATTACH, "Unattach Razor Boomerang");
			unattach.parameters.put(EventType.Parameter.OBJECT, razorBoomerang);
			this.addCost(unattach);

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			EventFactory damage = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "Razor Boomerang deals 1 damage to target creature or player.");
			damage.parameters.put(EventType.Parameter.SOURCE, razorBoomerang);
			damage.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			damage.parameters.put(EventType.Parameter.TAKER, target);

			this.addEffect(bounce(razorBoomerang, "Return Razor Boomerang to its owner's hand."));
		}
	}

	public static final class EquippedCreatureCanThrowMe extends StaticAbility
	{
		public EquippedCreatureCanThrowMe(GameState state)
		{
			super(state, "Equipped creature has \"(T), Unattach Razor Boomerang: Razor Boomerang deals 1 damage to target creature or player. Return Razor Boomerang to its owner's hand.\"");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), ThrowMe.class));
		}
	}

	public RazorBoomerang(GameState state)
	{
		super(state);

		// Equipped creature has
		// "(T), Unattach Razor Boomerang: Razor Boomerang deals 1 damage to target creature or player. Return Razor Boomerang to its owner's hand."
		this.addAbility(new EquippedCreatureCanThrowMe(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
