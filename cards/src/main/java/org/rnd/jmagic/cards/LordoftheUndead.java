package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lord of the Undead")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PLANESHIFT, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class LordoftheUndead extends Card
{
	public static final class Harvest extends ActivatedAbility
	{
		public Harvest(GameState state)
		{
			super(state, "(1)(B), (T): Return target Zombie card from your graveyard to your hand.");

			this.setManaCost(new ManaPool("1B"));

			this.costsTap = true;

			SetGenerator graveyard = GraveyardOf.instance(You.instance());
			Target target = this.addTarget(Intersect.instance(HasSubType.instance(SubType.ZOMBIE), InZone.instance(graveyard)), "target Zombie card from your graveyard");

			EventType.ParameterMap moveParameters = new EventType.ParameterMap();
			moveParameters.put(EventType.Parameter.CAUSE, This.instance());
			moveParameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			moveParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, moveParameters, "Return target Zombie card from your graveyard to your hand"));
		}
	}

	public LordoftheUndead(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		SetGenerator zombieCreatures = Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.ZOMBIE));
		SetGenerator others = RelativeComplement.instance(zombieCreatures, This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, others, "Other Zombie creatures", +1, +1, true));

		this.addAbility(new Harvest(state));
	}
}
