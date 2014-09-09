package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crypt of Agadeem")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK})
public final class CryptofAgadeem extends Card
{
	public static final class LotsOfBlackMana extends ActivatedAbility
	{
		public LotsOfBlackMana(GameState state)
		{
			super(state, "(2), (T): Add (B) to your mana pool for each black creature card in your graveyard.");
			this.setManaCost(new ManaPool("2"));
			this.costsTap = true;

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator creaturesInYourBin = Intersect.instance(InZone.instance(yourGraveyard), HasType.instance(Type.CREATURE));

			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add (B) to your mana pool for each black creature card in your graveyard.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.MANA, Identity.instance(Color.BLACK));
			mana.parameters.put(EventType.Parameter.NUMBER, Count.instance(creaturesInYourBin));
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(mana);
		}
	}

	public CryptofAgadeem(GameState state)
	{
		super(state);

		// Crypt of Agadeem enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForB(state));

		// (2), (T): Add (B) to your mana pool for each black creature card in
		// your graveyard.
		this.addAbility(new LotsOfBlackMana(state));
	}
}
