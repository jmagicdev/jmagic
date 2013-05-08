package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Throne of Empires")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({})
public final class ThroneofEmpires extends Card
{
	public static final class ThroneofEmpiresAbility0 extends ActivatedAbility
	{
		public ThroneofEmpiresAbility0(GameState state)
		{
			super(state, "(1), (T): Put a 1/1 white Soldier creature token onto the battlefield. Put five of those tokens onto the battlefield instead if you control artifacts named Crown of Empires and Scepter of Empires.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			SetGenerator scepter = Intersect.instance(ArtifactPermanents.instance(), HasName.instance("Scepter of Empires"));
			SetGenerator haveScepter = Intersect.instance(ControlledBy.instance(You.instance()), scepter);
			SetGenerator crown = Intersect.instance(ArtifactPermanents.instance(), HasName.instance("Crown of Empires"));
			SetGenerator haveCrown = Intersect.instance(ControlledBy.instance(You.instance()), crown);
			SetGenerator masterCollector = Both.instance(haveScepter, haveCrown);

			CreateTokensFactory oneToken = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Soldier creature token onto the battlefield.");
			oneToken.setColors(Color.WHITE);
			oneToken.setSubTypes(SubType.SOLDIER);

			CreateTokensFactory fiveTokens = new CreateTokensFactory(5, 1, 1, "Put five 1/1 white Soldier creature tokens onto the battlefield.");
			fiveTokens.setColors(Color.WHITE);
			fiveTokens.setSubTypes(SubType.SOLDIER);

			this.addEffect(ifThenElse(masterCollector, fiveTokens.getEventFactory(), oneToken.getEventFactory(), "Put a 1/1 white Soldier creature token onto the battlefield. Put five of those tokens onto the battlefield instead if you control artifacts named Crown of Empires and Scepter of Empires."));
		}
	}

	public ThroneofEmpires(GameState state)
	{
		super(state);

		// (1), (T): Put a 1/1 white Soldier creature token onto the
		// battlefield. Put five of those tokens onto the battlefield instead if
		// you control artifacts named Crown of Empires and Scepter of Empires.
		this.addAbility(new ThroneofEmpiresAbility0(state));
	}
}
