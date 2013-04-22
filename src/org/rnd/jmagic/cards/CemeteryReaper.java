package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cemetery Reaper")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class CemeteryReaper extends Card
{
	public static final class MakeZombie extends ActivatedAbility
	{
		public MakeZombie(GameState state)
		{
			super(state, "(2)(B), (T): Exile target creature card from a graveyard. Put a 2/2 black Zombie creature token onto the battlefield.");

			// (2)(B),
			this.setManaCost(new ManaPool("2B"));

			// (T):
			this.costsTap = true;

			// Exile target creature card from a graveyard.
			SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
			SetGenerator creaturesInYards = Intersect.instance(HasType.instance(Type.CREATURE), inGraveyards);
			Target target = this.addTarget(creaturesInYards, "target creature card from a graveyard");
			this.addEffect(exile(targetedBy(target), "Exile target creature card from a graveyard."));

			// Put a 2/2 black Zombie creature token onto the battlefield.
			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.ZOMBIE);
			this.addEffect(token.getEventFactory());
		}
	}

	public CemeteryReaper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other Zombie creatures you control
		SetGenerator zombies = Intersect.instance(HasSubType.instance(SubType.ZOMBIE), CREATURES_YOU_CONTROL);
		SetGenerator othersYouControl = RelativeComplement.instance(zombies, This.instance());

		// get +1/+1.
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, othersYouControl, "Other Zombie creatures you control", +1, +1, true));

		this.addAbility(new MakeZombie(state));
	}
}
