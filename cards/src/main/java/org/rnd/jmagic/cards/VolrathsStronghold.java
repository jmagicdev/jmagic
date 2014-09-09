package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Volrath's Stronghold")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@ColorIdentity({Color.BLACK})
public final class VolrathsStronghold extends Card
{
	public static final class VolrathsStrongholdAbility1 extends ActivatedAbility
	{
		public VolrathsStrongholdAbility1(GameState state)
		{
			super(state, "(1)(B), (T): Put target creature card from your graveyard on top of your library.");
			this.setManaCost(new ManaPool("(1)(B)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), "target creature card in your graveyard"));

			this.addEffect(putOnTopOfLibrary(target, "Put target creature card from your graveyard on top of your library."));
		}
	}

	public VolrathsStronghold(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1)(B), (T): Put target creature card from your graveyard on top of
		// your library.
		this.addAbility(new VolrathsStrongholdAbility1(state));
	}
}
