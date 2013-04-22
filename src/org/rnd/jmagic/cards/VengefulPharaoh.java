package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vengeful Pharaoh")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2BBB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class VengefulPharaoh extends Card
{
	public static final class VengefulPharaohAbility1 extends EventTriggeredAbility
	{
		public VengefulPharaohAbility1(GameState state)
		{
			super(state, "Whenever combat damage is dealt to you or a planeswalker you control, if Vengeful Pharaoh is in your graveyard, destroy target attacking creature, then put Vengeful Pharaoh on top of your library.");

			SetGenerator yours = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.PLANESWALKER));
			SetGenerator youAndYours = Union.instance(You.instance(), yours);
			this.addPattern(whenIsDealtCombatDamage(youAndYours));

			this.triggersFromGraveyard();
			this.interveningIf = ABILITY_SOURCE_IS_IN_GRAVEYARD;

			SetGenerator target = targetedBy(this.addTarget(Attacking.instance(), "target attacking creature"));
			this.addEffect(destroy(target, "Destroy target attacking creature,"));

			this.addEffect(putOnTopOfLibrary(ABILITY_SOURCE_OF_THIS, "then put Vengeful Pharaoh on top of your library."));
		}
	}

	public VengefulPharaoh(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Deathtouch (Any amount of damage this deals to a creature is enough
		// to destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Whenever combat damage is dealt to you or a planeswalker you control,
		// if Vengeful Pharaoh is in your graveyard, destroy target attacking
		// creature, then put Vengeful Pharaoh on top of your library.
		this.addAbility(new VengefulPharaohAbility1(state));
	}
}
